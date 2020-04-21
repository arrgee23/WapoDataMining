package trec;

public class Correlation {
	String first;
	String second;
	double value;
	
	 
	public Correlation(String first, String second, double value) {
		super();
		this.first = first;
		this.second = second;
		this.value = value;
	}
	
	static final Correlation[] TABLE = {
			new Correlation("Speaking of Science","To Your Health",0.152542372881),
			new Correlation("Style","Technology",0.0588235294118),
			new Correlation("Morning Mix","Letters to the Editor",0.0437158469945),
			new Correlation("WorldViews","Style",0.00621118012422),
			new Correlation("GovBeat","The Fix",0.0714285714286),
			new Correlation("Health & Science","To Your Health",0.0625),
			new Correlation("Politics","The Post's View",0.0588235294118),
			new Correlation("Gridlock","WorldViews",0.0322580645161),
			new Correlation("Wonkblog","On Small Business",0.0124481327801),
			new Correlation("National Security","Erik Wemple",0.00613496932515),
			new Correlation("Europe","PostEverything",0.0363636363636),
			new Correlation("Morning Mix","Investigations",0.0109289617486),
			new Correlation("Morning Mix","The Fix",0.00546448087432),
			new Correlation("Energy and Environment","The Americas",0.0357142857143),
			new Correlation("Wonkblog","Local",0.0435684647303),
			new Correlation("Wonkblog","The Watch",0.00622406639004),
			new Correlation("Politics","World",0.117647058824),
			new Correlation("Business","GovBeat",0.0169491525424),
			new Correlation("GovBeat","Wonkblog",0.309523809524),
			new Correlation("Transportation","Politics",0.0309278350515),
			new Correlation("Energy and Environment","Opinions",0.0714285714286),
			new Correlation("Speaking of Science","Style",0.0338983050847),
			new Correlation("Museums","Express",0.25),
			new Correlation("Style","Local",0.0588235294118),
			new Correlation("National Security","The Fix",0.0122699386503),
			new Correlation("To Your Health","Business",0.0491803278689),
			new Correlation("Asia & Pacific","Letters to the Editor",0.103448275862),
			new Correlation("Morning Mix","The Intersect",0.0601092896175),
			new Correlation("Energy and Environment","Wonkblog",0.0357142857143),
			new Correlation("Morning Mix","Inspired Life",0.016393442623),
			new Correlation("Capital Weather Gang","Food",0.0103092783505),
			new Correlation("Wonkblog","Asia & Pacific",0.00207468879668),
			new Correlation("Business","Perspective",0.0338983050847),
			new Correlation("Morning Mix","Innovations",0.0109289617486),
			new Correlation("Speaking of Science","Energy and Environment",0.0169491525424),
			new Correlation("Wonkblog","National",0.00829875518672),
			new Correlation("Wonkblog","Morning Mix",0.0165975103734),
			new Correlation("Maryland Politics","Wonkblog",0.130434782609),
			new Correlation("Gridlock","Politics",0.0967741935484),
			new Correlation("GovBeat","GovBeat",0.166666666667),
			new Correlation("National Security","WorldViews",0.0429447852761),
			new Correlation("Asia & Pacific","World",0.0344827586207),
			new Correlation("She The People","Education",0.0701754385965),
			new Correlation("Gridlock","National Security",0.0322580645161),
			new Correlation("Morning Mix","Opinions",0.0327868852459),
			new Correlation("WorldViews","Politics",0.0310559006211),
			new Correlation("Wonkblog","Animalia",0.00414937759336),
			new Correlation("Wonkblog","Transportation",0.00622406639004),
			new Correlation("Wonkblog","Right Turn",0.0124481327801),
			new Correlation("Wonkblog","Gridlock",0.00207468879668),
			new Correlation("Health & Science","The Fed Page",0.0625),
			new Correlation("Gridlock","Early Lead",0.0322580645161),
			new Correlation("Erik Wemple","Morning Mix",0.147058823529),
			new Correlation("The Switch","Business",0.1),
			new Correlation("Post Nation","Style",0.016393442623),
			new Correlation("Speaking of Science","The Fed Page",0.0169491525424),
			new Correlation("Morning Mix","Morning Mix",0.120218579235),
			new Correlation("Wonkblog","Business",0.051867219917),
			new Correlation("Wonkblog","National Security",0.00622406639004),
			new Correlation("She The People","Analysis",0.0350877192982),
			new Correlation("To Your Health","The Post's View",0.0491803278689),
			new Correlation("She The People","Storyline",0.0175438596491),
			new Correlation("National Security","Style",0.00613496932515),
			new Correlation("Energy and Environment","Brand Studio",0.0357142857143),
			new Correlation("Gridlock","Morning Mix",0.0322580645161),
			new Correlation("Post Nation","Going Out Guide",0.0327868852459),
			new Correlation("Morning Mix","WorldViews",0.0109289617486),
			new Correlation("WorldViews","Post Nation",0.00621118012422),
			new Correlation("Energy and Environment","PostEverything",0.0357142857143),
			new Correlation("Post Nation","National",0.0655737704918),
			new Correlation("Speaking of Science","Speaking of Science",0.338983050847),
			new Correlation("Transportation","Dr. Gridlock",0.0412371134021),
			new Correlation("Maryland Politics","Maryland Terrapins",0.0434782608696),
			new Correlation("To Your Health","Morning Mix",0.0245901639344),
			new Correlation("Capital Weather Gang","Morning Mix",0.0103092783505),
			new Correlation("Gridlock","Transportation",0.129032258065),
			new Correlation("Business","Energy and Environment",0.0677966101695),
			new Correlation("National Security","Morning Mix",0.00613496932515),
			new Correlation("The Switch","Gridlock",0.0166666666667),
			new Correlation("Europe","Opinions",0.0727272727273),
			new Correlation("Gridlock","Opinions",0.0322580645161),
			new Correlation("Gridlock","Tripping",0.0322580645161),
			new Correlation("Style","National",0.205882352941),
			new Correlation("Erik Wemple","Business",0.176470588235),
			new Correlation("WorldViews","World",0.0124223602484),
			new Correlation("Europe","Monkey Cage",0.0545454545455),
			new Correlation("Wonkblog","Cars",0.00207468879668),
			new Correlation("WorldViews","Opinions",0.0310559006211),
			new Correlation("Capital Weather Gang","Wonkblog",0.020618556701),
			new Correlation("Morning Mix","KidsPost",0.00546448087432),
			new Correlation("Wonkblog","Letters to the Editor",0.00207468879668),
			new Correlation("Health & Science","Wonkblog",0.125),
			new Correlation("Politics","The Fix",0.0588235294118),
			new Correlation("Wonkblog","Food",0.00207468879668),
			new Correlation("National Security","Early Lead",0.0184049079755),
			new Correlation("Post Nation","Analysis",0.0327868852459),
			new Correlation("GovBeat","Fact Checker",0.0238095238095),
			new Correlation("Wonkblog","On Leadership",0.00414937759336),
			new Correlation("GovBeat","National",0.047619047619),
			new Correlation("Wonkblog","PostPartisan",0.00414937759336),
			new Correlation("WorldViews","The Fix",0.0931677018634),
			new Correlation("Morning Mix","Business",0.016393442623),
			new Correlation("WorldViews","Fact Checker",0.00621118012422),
			new Correlation("To Your Health","America Answers",0.016393442623),
			new Correlation("Transportation","PostEverything",0.0103092783505),
			new Correlation("Maryland Politics","Local",0.173913043478),
			new Correlation("National Security","The Post's View",0.0184049079755),
			new Correlation("Health & Science","Wellness",0.0625),
			new Correlation("National Security","Comic Riffs",0.0674846625767),
			new Correlation("To Your Health","Federal Insider",0.016393442623),
			new Correlation("The Switch","WorldViews",0.0166666666667),
			new Correlation("To Your Health","Speaking of Science",0.0327868852459),
			new Correlation("Europe","On Leadership",0.00909090909091),
			new Correlation("Wonkblog","Energy and Environment",0.00207468879668),
			new Correlation("Post Nation","World",0.0983606557377),
			new Correlation("Capital Weather Gang","Speaking of Science",0.020618556701),
			new Correlation("Morning Mix","Entertainment",0.00546448087432),
			new Correlation("Morning Mix","Monkey Cage",0.00546448087432),
			new Correlation("Europe","Obituaries",0.00909090909091),
			new Correlation("Health & Science","Books",0.0625),
			new Correlation("Maryland Politics","Grade Point",0.130434782609),
			new Correlation("Wonkblog","Wonkblog",0.149377593361),
			new Correlation("Animalia","Asia & Pacific",0.0281690140845),
			new Correlation("Morning Mix","Magazine",0.00546448087432),
			new Correlation("Wonkblog","Answer Sheet",0.00414937759336),
			new Correlation("Wonkblog","Opinions",0.0124481327801),
			new Correlation("Post Nation","Local",0.0983606557377),
			new Correlation("Animalia","nan",0.0140845070423),
			new Correlation("Morning Mix","Movies",0.00546448087432),
			new Correlation("Gridlock","The Post's View",0.0322580645161),
			new Correlation("Politics","WorldViews",0.117647058824),
			new Correlation("Animalia","KidsPost",0.0281690140845),
			new Correlation("Animalia","Grade Point",0.0140845070423),
			new Correlation("Morning Mix","Storyline",0.00546448087432),
			new Correlation("Energy and Environment","World",0.0714285714286),
			new Correlation("The Americas","The Americas",0.333333333333),
			new Correlation("Erik Wemple","WorldViews",0.0882352941176),
			new Correlation("Wonkblog","Innovations",0.00207468879668),
			new Correlation("Morning Mix","The Posts View",0.00546448087432),
			new Correlation("WorldViews","The Post's View",0.00621118012422),
			new Correlation("She The People","Politics",0.0175438596491),
			new Correlation("Style","Innovations",0.0294117647059),
			new Correlation("She The People","nan",0.0350877192982),
			new Correlation("Wonkblog","Inspired Life",0.00414937759336),
			new Correlation("Wonkblog","The Volokh Conspiracy",0.00414937759336),
			new Correlation("Politics","Post Politics",0.176470588235),
			new Correlation("Wonkblog","District of DeBonis",0.00414937759336),
			new Correlation("National Security","Middle East",0.110429447853),
			new Correlation("Erik Wemple","Monkey Cage",0.0294117647059),
			new Correlation("Wonkblog","Books",0.00622406639004),
			new Correlation("Wonkblog","Real Estate",0.0145228215768),
			new Correlation("Transportation","The Switch",0.0103092783505),
			new Correlation("Asia & Pacific","WorldViews",0.206896551724),
			new Correlation("Erik Wemple","Opinion",0.0588235294118),
			new Correlation("Gridlock","World",0.0322580645161),
			new Correlation("Health & Science","Health & Science",0.0625),
			new Correlation("Wonkblog","D.C. Politics",0.0207468879668),
			new Correlation("The Switch","Washington Post Live",0.0166666666667),
			new Correlation("Erik Wemple","The Intersect",0.0294117647059),
			new Correlation("The Switch","Get There",0.116666666667),
			new Correlation("Speaking of Science","Innovations",0.0508474576271),
			new Correlation("The Switch","Monkey Cage",0.0166666666667),
			new Correlation("National Security","KidsPost",0.0184049079755),
			new Correlation("She The People","Post Politics",0.0175438596491),
			new Correlation("To Your Health","Opinions",0.0327868852459),
			new Correlation("WorldViews","She The People",0.105590062112),
			new Correlation("Capital Weather Gang","Going Out Guide",0.0515463917526),
			new Correlation("Business","On Small Business",0.0338983050847),
			new Correlation("Post Nation","The Americas",0.114754098361),
			new Correlation("Europe","Morning Mix",0.00909090909091),
			new Correlation("National Security","WashPost PR Blog",0.079754601227),
			new Correlation("The Switch","Technology",0.1),
			new Correlation("Capital Weather Gang","Express",0.0103092783505),
			new Correlation("WorldViews","Europe",0.00621118012422),
			new Correlation("She The People","Get There",0.0350877192982),
			new Correlation("The Switch","Federal Insider",0.05),
			new Correlation("Asia & Pacific","Books",0.0344827586207),
			new Correlation("Capital Weather Gang","KidsPost",0.0309278350515),
			new Correlation("Wonkblog","Post Nation",0.00829875518672),
			new Correlation("Business","Health & Science",0.0338983050847),
			new Correlation("Health & Science","World",0.125),
			new Correlation("WorldViews","Asia & Pacific",0.0310559006211),
			new Correlation("Morning Mix","To Your Health",0.0327868852459),
			new Correlation("Post Nation","Europe",0.0491803278689),
			new Correlation("Wonkblog","Early Lead",0.00207468879668),
			new Correlation("She The People","She The People",0.0526315789474),
			new Correlation("Asia & Pacific","National Security",0.0344827586207),
			new Correlation("National Security","World",0.122699386503),
			new Correlation("Gridlock","The Fix",0.0322580645161),
			new Correlation("Erik Wemple","On Leadership",0.0294117647059),
			new Correlation("The Switch","On Small Business",0.0333333333333),
			new Correlation("GovBeat","Opinions",0.047619047619),
			new Correlation("Morning Mix","Post Nation",0.0109289617486),
			new Correlation("Capital Weather Gang","World",0.144329896907),
			new Correlation("Wonkblog","Capital Business",0.00622406639004),
			new Correlation("Morning Mix","GovBeat",0.00546448087432),
			new Correlation("Style","Erik Wemple",0.0588235294118),
			new Correlation("Animalia","Achenblog",0.0140845070423),
			new Correlation("WorldViews","Gridlock",0.00621118012422),
			new Correlation("Erik Wemple","Arts and Entertainment",0.0294117647059),
			new Correlation("National Security","Politics",0.0245398773006),
			new Correlation("Wonkblog","Public Safety",0.0103734439834),
			new Correlation("Style","Capital Business",0.0294117647059),
			new Correlation("Asia & Pacific","The Fix",0.0344827586207),
			new Correlation("Wonkblog","Travel",0.00414937759336),
			new Correlation("Gridlock","Letters to the Editor",0.0322580645161),
			new Correlation("To Your Health","PowerPost",0.00819672131148),
			new Correlation("Gridlock","Post Nation",0.0322580645161),
			new Correlation("Post Nation","Politics",0.0491803278689),
			new Correlation("Style","On Leadership",0.0294117647059),
			new Correlation("Morning Mix","Maryland Politics",0.0437158469945),
			new Correlation("Animalia","Public Safety",0.0140845070423),
			new Correlation("WorldViews","On Leadership",0.055900621118),
			new Correlation("Morning Mix","On Leadership",0.00546448087432),
			new Correlation("Morning Mix","Soccer Insider",0.00546448087432),
			new Correlation("Animalia","Travel",0.0281690140845),
			new Correlation("Europe","Wonkblog",0.190909090909),
			new Correlation("Wonkblog","WorldViews",0.00207468879668),
			new Correlation("Morning Mix","Answer Sheet",0.00546448087432),
			new Correlation("Capital Weather Gang","Local",0.0721649484536),
			new Correlation("Animalia","WorldViews",0.0281690140845),
			new Correlation("To Your Health","Early Lead",0.016393442623),
			new Correlation("WorldViews","Tablet",0.00621118012422),
			new Correlation("Style","Politics",0.0588235294118),
			new Correlation("Post Nation","Wonkblog",0.016393442623),
			new Correlation("Morning Mix","On Parenting",0.0109289617486),
			new Correlation("Animalia","Arts and Entertainment",0.0140845070423),
			new Correlation("Business","Politics",0.0677966101695),
			new Correlation("The Switch","Home & Garden",0.0166666666667),
			new Correlation("Europe","Local",0.0818181818182),
			new Correlation("She The People","Wonkblog",0.19298245614),
			new Correlation("Style","Magazine",0.0294117647059),
			new Correlation("Wonkblog","Checkpoint",0.00207468879668),
			new Correlation("GovBeat","Food",0.0238095238095),
			new Correlation("Post Nation","Federal Insider",0.016393442623),
			new Correlation("Politics","Style",0.0588235294118),
			new Correlation("Speaking of Science","Morning Mix",0.0169491525424),
			new Correlation("Style","Right Turn",0.0294117647059),
			new Correlation("WorldViews","Letters to the Editor",0.0124223602484),
			new Correlation("Wonkblog","Fiscal Cliff",0.00207468879668),
			new Correlation("Morning Mix","blog",0.00546448087432),
			new Correlation("Morning Mix","Education",0.0327868852459),
			new Correlation("Wonkblog","Maryland",0.00207468879668),
			new Correlation("Wonkblog","Get There",0.00829875518672),
			new Correlation("To Your Health","Wellness",0.0409836065574),
			new Correlation("Maryland Politics","The Fix",0.0869565217391),
			new Correlation("Post Nation","GovBeat",0.016393442623),
			new Correlation("The Americas","WorldViews",0.666666666667),
			new Correlation("Speaking of Science","Business",0.0508474576271),
			new Correlation("Maryland Politics","On Leadership",0.0434782608696),
			new Correlation("GovBeat","The Post's View",0.0238095238095),
			new Correlation("Health & Science","WorldViews",0.125),
			new Correlation("Europe","Middle East",0.00909090909091),
			new Correlation("Europe","Letters to the Editor",0.00909090909091),
			new Correlation("To Your Health","Health & Science",0.139344262295),
			new Correlation("Style","The Switch",0.0294117647059),
			new Correlation("Capital Weather Gang","Perspective",0.020618556701),
			new Correlation("Transportation","Gridlock",0.134020618557),
			new Correlation("Business","Speaking of Science",0.0169491525424),
			new Correlation("Wonkblog","Health & Science",0.0186721991701),
			new Correlation("Post Nation","nan",0.016393442623),
			new Correlation("Morning Mix","Gridlock",0.00546448087432),
			new Correlation("Capital Weather Gang","Asia & Pacific",0.020618556701),
			new Correlation("The Switch","Style",0.0166666666667),
			new Correlation("Speaking of Science","Post Live",0.0169491525424),
			new Correlation("Animalia","Capital Weather Gang",0.0140845070423),
			new Correlation("Morning Mix","Wonkblog",0.0437158469945),
			new Correlation("National Security","National Security",0.269938650307),
			new Correlation("Business","Opinions",0.0338983050847),
			new Correlation("Morning Mix","The Volokh Conspiracy",0.00546448087432),
			new Correlation("Animalia","Health & Science",0.0281690140845),
			new Correlation("WorldViews","Maryland Politics",0.0124223602484),
			new Correlation("National Security","Post Nation",0.0736196319018),
			new Correlation("Gridlock","Gridlock",0.161290322581),
			new Correlation("The Switch","Grade Point",0.0166666666667),
			new Correlation("Europe","World",0.0181818181818),
			new Correlation("The Switch","Morning Mix",0.0666666666667),
			new Correlation("WorldViews","Wonkblog",0.0186335403727),
			new Correlation("Asia & Pacific","Monkey Cage",0.0344827586207),
			new Correlation("Wonkblog","She The People",0.0103734439834),
			new Correlation("Morning Mix","Politics",0.0218579234973),
			new Correlation("WorldViews","GovBeat",0.00621118012422),
			new Correlation("Wonkblog","Sports",0.00414937759336),
			new Correlation("GovBeat","Perspective",0.0238095238095),
			new Correlation("Europe","Business",0.118181818182),
			new Correlation("Capital Weather Gang","Energy and Environment",0.020618556701),
			new Correlation("To Your Health","The Posts View",0.00819672131148),
			new Correlation("Wonkblog","Europe",0.00207468879668),
			new Correlation("National Security","PostEverything",0.0122699386503),
			new Correlation("Animalia","The Switch",0.0140845070423),
			new Correlation("WorldViews","PowerPost",0.0248447204969),
			new Correlation("Energy and Environment","The Post's View",0.0357142857143),
			new Correlation("Morning Mix","Acts of Faith",0.00546448087432),
			new Correlation("Wonkblog","Opinion",0.0228215767635),
			new Correlation("Post Nation","Post Nation",0.0491803278689),
			new Correlation("Business","Wonkblog",0.186440677966),
			new Correlation("Wonkblog","Monkey Cage",0.00414937759336),
			new Correlation("GovBeat","Rampage",0.047619047619),
			new Correlation("Gridlock","In the Loop",0.0322580645161),
			new Correlation("The Switch","Real Estate",0.0333333333333),
			new Correlation("Wonkblog","The Plum Line",0.0871369294606),
			new Correlation("The Switch","The Switch",0.183333333333),
			new Correlation("WorldViews","WorldViews",0.223602484472),
			new Correlation("Politics","Politics",0.352941176471),
			new Correlation("Wonkblog","Post Politics",0.0539419087137),
			new Correlation("Speaking of Science","KidsPost",0.0169491525424),
			new Correlation("Europe","National",0.0818181818182),
			new Correlation("Transportation","Innovations",0.0103092783505),
			new Correlation("Morning Mix","Europe",0.00546448087432),
			new Correlation("Speaking of Science","Opinions",0.0338983050847),
			new Correlation("She The People","Business",0.140350877193),
			new Correlation("She The People","D.C. Politics",0.0175438596491),
			new Correlation("Post Nation","Museums",0.0655737704918),
			new Correlation("Morning Mix","Speaking of Science",0.00546448087432),
			new Correlation("Wonkblog","Test",0.00207468879668),
			new Correlation("Capital Weather Gang","Capital Weather Gang",0.484536082474),
			new Correlation("Business","KidsPost",0.0169491525424),
			new Correlation("Business","Cars",0.0169491525424),
			new Correlation("GovBeat","Opinion",0.0238095238095),
			new Correlation("Gridlock","Europe",0.0322580645161),
			new Correlation("Maryland Politics","Public Safety",0.0434782608696),
			new Correlation("WorldViews","Middle East",0.0310559006211),
			new Correlation("The Switch","Wonkblog",0.0166666666667),
			new Correlation("National Security","PowerPost",0.0245398773006),
			new Correlation("GovBeat","WashPost PR Blog",0.0238095238095),
			new Correlation("To Your Health","Letters to the Editor",0.00819672131148),
			new Correlation("Transportation","Local",0.0515463917526),
			new Correlation("Energy and Environment","Energy and Environment",0.678571428571),
			new Correlation("National Security","Europe",0.0122699386503),
			new Correlation("Wonkblog","Politics",0.097510373444),
			new Correlation("Wonkblog","PostEverything",0.0352697095436),
			new Correlation("Health & Science","The Post's View",0.0625),
			new Correlation("The Switch","Politics",0.0166666666667),
			new Correlation("Erik Wemple","The Switch",0.264705882353),
			new Correlation("Morning Mix","Transportation",0.0109289617486),
			new Correlation("To Your Health","Food",0.016393442623),
			new Correlation("Post Nation","WorldViews",0.016393442623),
			new Correlation("Wonkblog","The Fix",0.0435684647303),
			new Correlation("Post Nation","Post Politics",0.0655737704918),
			new Correlation("Maryland Politics","Opinion",0.0869565217391),
			new Correlation("Morning Mix","True Crime",0.00546448087432),
			new Correlation("The Switch","Ask The Post",0.0166666666667),
			new Correlation("Morning Mix","Early Lead",0.0109289617486),
			new Correlation("Museums","Books",0.25),
			new Correlation("Business","Capital Business",0.118644067797),
			new Correlation("WorldViews","Analysis",0.0310559006211),
			new Correlation("WorldViews","Monkey Cage",0.192546583851),
			new Correlation("Style","Post Local",0.0294117647059),
			new Correlation("Business","Business",0.152542372881),
			new Correlation("GovBeat","She The People",0.0238095238095),
			new Correlation("Wonkblog","KidsPost",0.00207468879668),
			new Correlation("Animalia","Morning Mix",0.267605633803),
			new Correlation("Wonkblog","Speaking of Science",0.00207468879668),
			new Correlation("Erik Wemple","National Security",0.0294117647059),
			new Correlation("The Switch","Dr. Gridlock",0.0166666666667),
			new Correlation("Politics","Virginia Politics",0.0588235294118),
			new Correlation("Capital Weather Gang","Transportation",0.0103092783505),
			new Correlation("Morning Mix","The Switch",0.0546448087432),
			new Correlation("Transportation","Transportation",0.298969072165),
			new Correlation("Post Nation","Immigration",0.0491803278689),
			new Correlation("Speaking of Science","Health & Science",0.186440677966),
			new Correlation("Erik Wemple","World",0.0294117647059),
			new Correlation("Europe","Europe",0.236363636364),
			new Correlation("Transportation","The Post's View",0.0103092783505),
			new Correlation("Animalia","Local",0.056338028169),
			new Correlation("To Your Health","Wonkblog",0.311475409836),
			new Correlation("The Switch","Travel",0.0666666666667),
			new Correlation("She The People","Innovations",0.0175438596491),
			new Correlation("Wonkblog","Storyline",0.00622406639004),
			new Correlation("She The People","Grade Point",0.122807017544),
			new Correlation("Wonkblog","nan",0.00207468879668),
			new Correlation("Animalia","Early Lead",0.0422535211268),
			new Correlation("Transportation","Business",0.278350515464),
			new Correlation("Wonkblog","Education",0.00414937759336),
			new Correlation("Wonkblog","GovBeat",0.0622406639004),
			new Correlation("Transportation","D.C. Politics",0.0412371134021),
			new Correlation("Transportation","WashPost PR Blog",0.020618556701),
			new Correlation("Europe","PostPartisan",0.00909090909091),
			new Correlation("Erik Wemple","The Volokh Conspiracy",0.0294117647059),
			new Correlation("She The People","Opinions",0.0526315789474),
			new Correlation("Morning Mix","Health & Science",0.103825136612),
			new Correlation("Wonkblog","Virginia Politics",0.00207468879668),
			new Correlation("Asia & Pacific","National",0.0344827586207),
			new Correlation("The Switch","Capital Business",0.0333333333333),
			new Correlation("Wonkblog","Post Local",0.00207468879668),
			new Correlation("Morning Mix","World",0.016393442623),
			new Correlation("Maryland Politics","Maryland Politics",0.130434782609),
			new Correlation("Business","The Switch",0.118644067797),
			new Correlation("National Security","Post Politics",0.0429447852761),
			new Correlation("Maryland Politics","The Post's View",0.0434782608696),
			new Correlation("Wonkblog","The Fed Page",0.00207468879668),
			new Correlation("Wonkblog","Fact Checker",0.0165975103734),
			new Correlation("National Security","Opinion",0.0184049079755),
			new Correlation("Museums","Museums",0.5),
			new Correlation("GovBeat","Local",0.0238095238095),
			new Correlation("She The People","Letters to the Editor",0.0175438596491),
			new Correlation("Wonkblog","Perspective",0.00207468879668),
			new Correlation("Morning Mix","Opinion",0.00546448087432),
			new Correlation("She The People","Answer Sheet",0.140350877193),
			new Correlation("WorldViews","Post Politics",0.0248447204969),
			new Correlation("Maryland Politics","KidsPost",0.0434782608696),
			new Correlation("GovBeat","Politics",0.119047619048),
			new Correlation("Gridlock","Federal Insider",0.0645161290323),
			new Correlation("The Switch","Perspective",0.0166666666667),
			new Correlation("Capital Weather Gang","Health & Science",0.0515463917526),
			new Correlation("Europe","The Posts View",0.0181818181818),
			new Correlation("Morning Mix","Technology",0.0218579234973),
			new Correlation("Morning Mix","She The People",0.0273224043716),
			new Correlation("Asia & Pacific","Opinions",0.0344827586207),
			new Correlation("Erik Wemple","Erik Wemple",0.0588235294118),
			new Correlation("To Your Health","PostEverything",0.016393442623),
			new Correlation("Post Nation","Movies",0.016393442623),
			new Correlation("Animalia","National",0.0281690140845),
			new Correlation("Wonkblog","Going Out Guide",0.00207468879668),
			new Correlation("Morning Mix","Local",0.0382513661202),
			new Correlation("WorldViews","National Security",0.0124223602484),
			new Correlation("The Switch","Investigations",0.0166666666667),
			new Correlation("WorldViews","Opinion",0.00621118012422),
			new Correlation("Post Nation","Magazine",0.016393442623),
			new Correlation("Style","The Fix",0.0588235294118),
			new Correlation("Morning Mix","D.C. Politics",0.016393442623),
			new Correlation("Animalia","Speaking of Science",0.0704225352113),
			new Correlation("Animalia","Animalia",0.225352112676),
			new Correlation("Wonkblog","To Your Health",0.0103734439834),
			new Correlation("Transportation","Morning Mix",0.0103092783505),
			new Correlation("Europe","Education",0.00909090909091),
			new Correlation("Asia & Pacific","Asia & Pacific",0.241379310345),
			new Correlation("Post Nation","Public Safety",0.016393442623),
			new Correlation("To Your Health","To Your Health",0.213114754098),
			new Correlation("Capital Weather Gang","WorldViews",0.020618556701),
			new Correlation("Post Nation","Monkey Cage",0.016393442623),
			new Correlation("Asia & Pacific","The Post's View",0.0689655172414),
			new Correlation("Transportation","Wonkblog",0.0515463917526),
			new Correlation("Morning Mix","PostEverything",0.0218579234973),
			new Correlation("Asia & Pacific","Wonkblog",0.137931034483),
			new Correlation("Style","Business",0.176470588235),
			new Correlation("Wonkblog","Federal Insider",0.00622406639004),
			new Correlation("Maryland Politics","Retropolis",0.0434782608696),
			new Correlation("Speaking of Science","Wonkblog",0.0338983050847),
			new Correlation("Animalia","Energy and Environment",0.0281690140845),
			new Correlation("Morning Mix","The Post's View",0.016393442623),
			new Correlation("Gridlock","Checkpoint",0.0967741935484),
			new Correlation("She The People","Federal Insider",0.0175438596491),
			new Correlation("Morning Mix","National",0.0327868852459),
			new Correlation("Business","Innovations",0.0847457627119),
			new Correlation("Animalia","Post Nation",0.0281690140845),
			new Correlation("Wonkblog","Maryland Politics",0.0352697095436),
			new Correlation("Animalia","Politics",0.0140845070423),
			new Correlation("Europe","WorldViews",0.0272727272727),
			new Correlation("Speaking of Science","Opinion",0.0338983050847),
			new Correlation("National Security","National",0.0122699386503),
			new Correlation("Gridlock","PowerPost",0.0322580645161),
			new Correlation("Style","Wonkblog",0.117647058824),
			new Correlation("Health & Science","Asia & Pacific",0.25),
			new Correlation("Wonkblog","In Theory",0.00207468879668),
			new Correlation("Post Nation","Morning Mix",0.0655737704918),
				
		}; 
	
	static double getScore(String a,String b) {
		
		boolean firstFound = false;
		double min = 9999;
		
		for(Correlation t : TABLE) {
			if(a.equals(t.first)) {
				firstFound = true;
				if(t.value<min)
					min = t.value;
				if(b.equals(t.second)) {
					return t.value;
				}
			}
		}
		
		if(firstFound)
			return min/1.1;
		else
			return 0.75;
	}
	public static void main(String[] args) {
		System.out.println(2/(double)3);

	}
	

}
