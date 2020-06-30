
package trec;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;

public class IndexFiles {

	@SuppressWarnings("deprecation")
	public static void createWapoIndex() {
		JsonFactory f = new MappingJsonFactory();
		JsonParser jp = null;
		try {
			File fl = new File(Constants.FILE_NAME);
			jp = f.createJsonParser(fl);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Report r = null;

		/* Index the documents */
		String indexPath = Constants.INDEX_PATH;
		/// String docsPath = null;
		boolean append = true;
		boolean createindex = true; // re index all
		boolean nerIndex = true;

		Directory directory = null;
		try {
			directory = FSDirectory.open(Paths.get(indexPath));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Analyzer analyzer = new EnglishAnalyzer(); // ..standard english word tokenization
		String serializedClassifier = "/home/i3/Documents/samford-ner-4/stanford-ner-4.0.0/classifiers/english.all.3class.distsim.crf.ser.gz";
		AbstractSequenceClassifier<CoreLabel> classifier = null;
		try {
			classifier = CRFClassifier.getClassifier(serializedClassifier);
		} catch (ClassCastException | ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (createindex) {
			try {
				System.out.println("Indexing to directory '" + indexPath + "'...");

				// Analyzer analyzer = new StandardAnalyzer();
				IndexWriterConfig config = new IndexWriterConfig(analyzer);
				// System.out.println(config.getSimilarity().toString());
				if (!append) {
					// Create a new index in the directory, removing any
					// previously indexed documents:
					config.setOpenMode(OpenMode.CREATE);
				} else {
					// Add new documents to an existing index:
					config.setOpenMode(OpenMode.CREATE_OR_APPEND);
				}
				IndexWriter iwriter = new IndexWriter(directory, config);
				// iwriter.

				// Optional: for better indexing performance, if you
				// are indexing many documents, increase the RAM
				// buffer. But if you do this, increase the max heap
				// size to the JVM (eg add -Xmx512m or -Xmx1g):
				//
				// config.setRAMBufferSizeMB(256.0);

				// read one entry from json and add it to index
				Document d = null;
				int i = 0;
				int total = 0;
				for (i = 0; i < Constants.TOTAL_DOC_NO; i++) {
					d = new Document();
					// System.out.println("Indexing doc: " + ++i);
					// parse a document from json and store in index

					r = ParsingOnly.readOneReport3(jp);

					if (r == null)
						continue;// Add fields to the document

					// if(r.id.equals("-1"))
					// break;

					// StringField.TYPE_STORED store the content as a single token
					if (r.id != null) {
						Field id = new Field("id", r.id, StringField.TYPE_STORED);
						d.add(id);
					} else
						continue;

					if (r.articleType != null) {
						Field articleType = new Field("articleType", r.articleType, StringField.TYPE_STORED);
						d.add(articleType);
					} else {
						Field articleType = new Field("articleType", "", StringField.TYPE_STORED);
						d.add(articleType);
					}

					if (r.author != null) {
						Field author = new Field("author", r.author, StringField.TYPE_STORED);
						d.add(author);
					} else {
						Field author = new Field("author", "", StringField.TYPE_STORED);
						d.add(author);
					}

					// TextField.TYPE_STORED tokenize the strings

					// this for making vectors
					FieldType ft = new FieldType(TextField.TYPE_STORED);
					ft.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
					ft.setStoreTermVectors(true);
					ft.setStoreTermVectorOffsets(true);
					ft.setStoreTermVectorPositions(true);
					ft.setStoreTermVectorPayloads(true);
					ft.setStored(true);
					boolean unique_index = false;

					// try to concatenate title with content a few times
					if (unique_index) {

						if (r.content != null) {
							String buffer = r.content;
							if (r.title != null && r.title.length() != 0) {
								int factor = (int) Math
										.round(Math.sqrt(r.content.length() / (double) r.title.length()));

								for (int m = 0; m < factor; m++) {
									buffer += r.title;
								}
							}
							Field content = new Field("content", buffer, ft);

							// content.term
							d.add(content);
						} else {
							Field content = new Field("content", "", ft);
							d.add(content);
						}

					}

					else {
						if (r.content != null) {

							Field content = new Field("content", r.content, ft);
							// content.term
							d.add(content);
							if (nerIndex) {
								List<Triple<String, Integer, Integer>> triples = classifier
										.classifyToCharacterOffsets(r.content);
								StringBuffer sb = new StringBuffer();
								int len = 0;
								for (Triple<String, Integer, Integer> trip : triples) {
									String tag = trip.first();
									String s = r.content.substring(trip.second, trip.third);
									String toAppend = tag + "/" + s + " ";
									sb.append(toAppend);
									len += toAppend.length();
									
									if(len>28000)
										break;
									/*
									 * System.out.
									 * printf("%s over string: <%s> character offsets [%d, %d) in sentence %d.%n",
									 * trip.first(), str.substring(trip.second, trip.third) ,trip.second(),
									 * trip.third, j);
									 */
								}
								Field contentner = new Field("contentner", sb.toString(), StringField.TYPE_STORED);
								d.add(contentner);
							}
						} else {
							Field content = new Field("content", "", ft);
							d.add(content);
							if (nerIndex) {
								Field contentner = new Field("contentner", "", StringField.TYPE_STORED);
								d.add(contentner);
							}
						}
					}
					if (r.date != null) {
						Field date = new Field("date", r.date, StringField.TYPE_STORED);
						// date.
						d.add(date);
					} else {
						Field date = new Field("date", "", StringField.TYPE_STORED);
						// date.
						d.add(date);
					}

					if (r.source != null) {
						Field source = new Field("source", r.source, StringField.TYPE_STORED);
						// Field f = new Field
						d.add(source);
					} else {
						Field source = new Field("source", "", StringField.TYPE_STORED);
						// Field f = new Field
						d.add(source);
					}

					if (r.title != null) {

						Field title = new Field("title", r.title, ft);
						d.add(title);
						if (nerIndex) {
							List<Triple<String, Integer, Integer>> triples = classifier
									.classifyToCharacterOffsets(r.title);
							StringBuffer sb = new StringBuffer();
							for (Triple<String, Integer, Integer> trip : triples) {
								sb.append(trip.first() + "/" + r.title.substring(trip.second, trip.third) + " ");
								/*
								 * System.out.
								 * printf("%s over string: <%s> character offsets [%d, %d) in sentence %d.%n",
								 * trip.first(), str.substring(trip.second, trip.third) ,trip.second(),
								 * trip.third, j);
								 */
							}
							Field titlener = new Field("titlener", sb.toString(), StringField.TYPE_STORED);
							// Field title = new Field("title", r.title,
							// TextField.TYPE_STORED,Field.TermVector.YES);
							d.add(titlener);
						}

					} else {
						Field title = new Field("title", "", ft);
						// Field title = new Field("title", r.title,
						// TextField.TYPE_STORED,Field.TermVector.YES);
						d.add(title);
						if (nerIndex) {
							Field titlener = new Field("titlener", "", StringField.TYPE_STORED);
							d.add(titlener);
						}
					}

					if (r.url != null) {
						Field url = new Field("url", r.url, StringField.TYPE_STORED);
						d.add(url);
					} else {
						Field url = new Field("url", "", StringField.TYPE_STORED);
						d.add(url);
					}
					// add the document to index
					iwriter.addDocument(d);

					total++;

					if ((i + 1) % 1000 == 0) {
						System.out.printf("Indexed %d documents...\n", i + 1);
					}
				}

				// System.out.printf("Indexed total %d documents...\n", i + 1);

				// NOTE: if you want to maximize search performance,
				// you can optionally call forceMerge here. This can be
				// a terribly costly operation, so generally it's only
				// worth it when your index is relatively static (ie
				// you're done adding documents to it):
				//
				// writer.forceMerge(1);

				iwriter.close(); // after adding docs close the indexwriter
				System.out.printf("loop %d...\n", i);
				System.out.printf("Total %d documents...\n", total);

			} catch (Exception e) {
				System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
			}

		}

		else
			System.out.println("Index already in directory '" + indexPath + "'...");

	}

	public static void main(String[] args) {
		createWapoIndex();
	}
}
