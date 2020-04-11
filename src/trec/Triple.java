package trec;

public class Triple {
	String first;
	String second;
	double value;
	
	 
	public Triple(String first, String second, double value) {
		super();
		this.first = first;
		this.second = second;
		this.value = value;
	}
	
	static final Triple[] TABLE = {
			new Triple("Speaking of Science","To Your Health",0.152542372881),
			new Triple("Style","Technology",0.0588235294118),
			new Triple("Morning Mix","Letters to the Editor",0.0437158469945),
			new Triple("WorldViews","Style",0.00621118012422),
			new Triple("GovBeat","The Fix",0.0714285714286),
			new Triple("Health & Science","To Your Health",0.0625),
			new Triple("Politics","The Post's View",0.0588235294118),
			new Triple("Gridlock","WorldViews",0.0322580645161),
			new Triple("Wonkblog","On Small Business",0.0124481327801),
			new Triple("National Security","Erik Wemple",0.00613496932515),
			new Triple("Europe","PostEverything",0.0363636363636),
			new Triple("Morning Mix","Investigations",0.0109289617486),
			new Triple("Morning Mix","The Fix",0.00546448087432),
			new Triple("Energy and Environment","The Americas",0.0357142857143),
			new Triple("Wonkblog","Local",0.0435684647303),
			new Triple("Wonkblog","The Watch",0.00622406639004),
			new Triple("Politics","World",0.117647058824),
			new Triple("Business","GovBeat",0.0169491525424),
			new Triple("GovBeat","Wonkblog",0.309523809524),
			new Triple("Transportation","Politics",0.0309278350515),
			new Triple("Energy and Environment","Opinions",0.0714285714286),
			new Triple("Speaking of Science","Style",0.0338983050847),
			new Triple("Museums","Express",0.25),
			new Triple("Style","Local",0.0588235294118),
			new Triple("National Security","The Fix",0.0122699386503),
			new Triple("To Your Health","Business",0.0491803278689),
			new Triple("Asia & Pacific","Letters to the Editor",0.103448275862),
			new Triple("Morning Mix","The Intersect",0.0601092896175),
			new Triple("Energy and Environment","Wonkblog",0.0357142857143),
			new Triple("Morning Mix","Inspired Life",0.016393442623),
			new Triple("Capital Weather Gang","Food",0.0103092783505),
			new Triple("Wonkblog","Asia & Pacific",0.00207468879668),
			new Triple("Business","Perspective",0.0338983050847),
			new Triple("Morning Mix","Innovations",0.0109289617486),
			new Triple("Speaking of Science","Energy and Environment",0.0169491525424),
			new Triple("Wonkblog","National",0.00829875518672),
			new Triple("Wonkblog","Morning Mix",0.0165975103734),
			new Triple("Maryland Politics","Wonkblog",0.130434782609),
			new Triple("Gridlock","Politics",0.0967741935484),
			new Triple("GovBeat","GovBeat",0.166666666667),
			new Triple("National Security","WorldViews",0.0429447852761),
			new Triple("Asia & Pacific","World",0.0344827586207),
			new Triple("She The People","Education",0.0701754385965),
			new Triple("Gridlock","National Security",0.0322580645161),
			new Triple("Morning Mix","Opinions",0.0327868852459),
			new Triple("WorldViews","Politics",0.0310559006211),
			new Triple("Wonkblog","Animalia",0.00414937759336),
			new Triple("Wonkblog","Transportation",0.00622406639004),
			new Triple("Wonkblog","Right Turn",0.0124481327801),
			new Triple("Wonkblog","Gridlock",0.00207468879668),
			new Triple("Health & Science","The Fed Page",0.0625),
			new Triple("Gridlock","Early Lead",0.0322580645161),
			new Triple("Erik Wemple","Morning Mix",0.147058823529),
			new Triple("The Switch","Business",0.1),
			new Triple("Post Nation","Style",0.016393442623),
			new Triple("Speaking of Science","The Fed Page",0.0169491525424),
			new Triple("Morning Mix","Morning Mix",0.120218579235),
			new Triple("Wonkblog","Business",0.051867219917),
			new Triple("Wonkblog","National Security",0.00622406639004),
			new Triple("She The People","Analysis",0.0350877192982),
			new Triple("To Your Health","The Post's View",0.0491803278689),
			new Triple("She The People","Storyline",0.0175438596491),
			new Triple("National Security","Style",0.00613496932515),
			new Triple("Energy and Environment","Brand Studio",0.0357142857143),
			new Triple("Gridlock","Morning Mix",0.0322580645161),
			new Triple("Post Nation","Going Out Guide",0.0327868852459),
			new Triple("Morning Mix","WorldViews",0.0109289617486),
			new Triple("WorldViews","Post Nation",0.00621118012422),
			new Triple("Energy and Environment","PostEverything",0.0357142857143),
			new Triple("Post Nation","National",0.0655737704918),
			new Triple("Speaking of Science","Speaking of Science",0.338983050847),
			new Triple("Transportation","Dr. Gridlock",0.0412371134021),
			new Triple("Maryland Politics","Maryland Terrapins",0.0434782608696),
			new Triple("To Your Health","Morning Mix",0.0245901639344),
			new Triple("Capital Weather Gang","Morning Mix",0.0103092783505),
			new Triple("Gridlock","Transportation",0.129032258065),
			new Triple("Business","Energy and Environment",0.0677966101695),
			new Triple("National Security","Morning Mix",0.00613496932515),
			new Triple("The Switch","Gridlock",0.0166666666667),
			new Triple("Europe","Opinions",0.0727272727273),
			new Triple("Gridlock","Opinions",0.0322580645161),
			new Triple("Gridlock","Tripping",0.0322580645161),
			new Triple("Style","National",0.205882352941),
			new Triple("Erik Wemple","Business",0.176470588235),
			new Triple("WorldViews","World",0.0124223602484),
			new Triple("Europe","Monkey Cage",0.0545454545455),
			new Triple("Wonkblog","Cars",0.00207468879668),
			new Triple("WorldViews","Opinions",0.0310559006211),
			new Triple("Capital Weather Gang","Wonkblog",0.020618556701),
			new Triple("Morning Mix","KidsPost",0.00546448087432),
			new Triple("Wonkblog","Letters to the Editor",0.00207468879668),
			new Triple("Health & Science","Wonkblog",0.125),
			new Triple("Politics","The Fix",0.0588235294118),
			new Triple("Wonkblog","Food",0.00207468879668),
			new Triple("National Security","Early Lead",0.0184049079755),
			new Triple("Post Nation","Analysis",0.0327868852459),
			new Triple("GovBeat","Fact Checker",0.0238095238095),
			new Triple("Wonkblog","On Leadership",0.00414937759336),
			new Triple("GovBeat","National",0.047619047619),
			new Triple("Wonkblog","PostPartisan",0.00414937759336),
			new Triple("WorldViews","The Fix",0.0931677018634),
			new Triple("Morning Mix","Business",0.016393442623),
			new Triple("WorldViews","Fact Checker",0.00621118012422),
			new Triple("To Your Health","America Answers",0.016393442623),
			new Triple("Transportation","PostEverything",0.0103092783505),
			new Triple("Maryland Politics","Local",0.173913043478),
			new Triple("National Security","The Post's View",0.0184049079755),
			new Triple("Health & Science","Wellness",0.0625),
			new Triple("National Security","Comic Riffs",0.0674846625767),
			new Triple("To Your Health","Federal Insider",0.016393442623),
			new Triple("The Switch","WorldViews",0.0166666666667),
			new Triple("To Your Health","Speaking of Science",0.0327868852459),
			new Triple("Europe","On Leadership",0.00909090909091),
			new Triple("Wonkblog","Energy and Environment",0.00207468879668),
			new Triple("Post Nation","World",0.0983606557377),
			new Triple("Capital Weather Gang","Speaking of Science",0.020618556701),
			new Triple("Morning Mix","Entertainment",0.00546448087432),
			new Triple("Morning Mix","Monkey Cage",0.00546448087432),
			new Triple("Europe","Obituaries",0.00909090909091),
			new Triple("Health & Science","Books",0.0625),
			new Triple("Maryland Politics","Grade Point",0.130434782609),
			new Triple("Wonkblog","Wonkblog",0.149377593361),
			new Triple("Animalia","Asia & Pacific",0.0281690140845),
			new Triple("Morning Mix","Magazine",0.00546448087432),
			new Triple("Wonkblog","Answer Sheet",0.00414937759336),
			new Triple("Wonkblog","Opinions",0.0124481327801),
			new Triple("Post Nation","Local",0.0983606557377),
			new Triple("Animalia","nan",0.0140845070423),
			new Triple("Morning Mix","Movies",0.00546448087432),
			new Triple("Gridlock","The Post's View",0.0322580645161),
			new Triple("Politics","WorldViews",0.117647058824),
			new Triple("Animalia","KidsPost",0.0281690140845),
			new Triple("Animalia","Grade Point",0.0140845070423),
			new Triple("Morning Mix","Storyline",0.00546448087432),
			new Triple("Energy and Environment","World",0.0714285714286),
			new Triple("The Americas","The Americas",0.333333333333),
			new Triple("Erik Wemple","WorldViews",0.0882352941176),
			new Triple("Wonkblog","Innovations",0.00207468879668),
			new Triple("Morning Mix","The Posts View",0.00546448087432),
			new Triple("WorldViews","The Post's View",0.00621118012422),
			new Triple("She The People","Politics",0.0175438596491),
			new Triple("Style","Innovations",0.0294117647059),
			new Triple("She The People","nan",0.0350877192982),
			new Triple("Wonkblog","Inspired Life",0.00414937759336),
			new Triple("Wonkblog","The Volokh Conspiracy",0.00414937759336),
			new Triple("Politics","Post Politics",0.176470588235),
			new Triple("Wonkblog","District of DeBonis",0.00414937759336),
			new Triple("National Security","Middle East",0.110429447853),
			new Triple("Erik Wemple","Monkey Cage",0.0294117647059),
			new Triple("Wonkblog","Books",0.00622406639004),
			new Triple("Wonkblog","Real Estate",0.0145228215768),
			new Triple("Transportation","The Switch",0.0103092783505),
			new Triple("Asia & Pacific","WorldViews",0.206896551724),
			new Triple("Erik Wemple","Opinion",0.0588235294118),
			new Triple("Gridlock","World",0.0322580645161),
			new Triple("Health & Science","Health & Science",0.0625),
			new Triple("Wonkblog","D.C. Politics",0.0207468879668),
			new Triple("The Switch","Washington Post Live",0.0166666666667),
			new Triple("Erik Wemple","The Intersect",0.0294117647059),
			new Triple("The Switch","Get There",0.116666666667),
			new Triple("Speaking of Science","Innovations",0.0508474576271),
			new Triple("The Switch","Monkey Cage",0.0166666666667),
			new Triple("National Security","KidsPost",0.0184049079755),
			new Triple("She The People","Post Politics",0.0175438596491),
			new Triple("To Your Health","Opinions",0.0327868852459),
			new Triple("WorldViews","She The People",0.105590062112),
			new Triple("Capital Weather Gang","Going Out Guide",0.0515463917526),
			new Triple("Business","On Small Business",0.0338983050847),
			new Triple("Post Nation","The Americas",0.114754098361),
			new Triple("Europe","Morning Mix",0.00909090909091),
			new Triple("National Security","WashPost PR Blog",0.079754601227),
			new Triple("The Switch","Technology",0.1),
			new Triple("Capital Weather Gang","Express",0.0103092783505),
			new Triple("WorldViews","Europe",0.00621118012422),
			new Triple("She The People","Get There",0.0350877192982),
			new Triple("The Switch","Federal Insider",0.05),
			new Triple("Asia & Pacific","Books",0.0344827586207),
			new Triple("Capital Weather Gang","KidsPost",0.0309278350515),
			new Triple("Wonkblog","Post Nation",0.00829875518672),
			new Triple("Business","Health & Science",0.0338983050847),
			new Triple("Health & Science","World",0.125),
			new Triple("WorldViews","Asia & Pacific",0.0310559006211),
			new Triple("Morning Mix","To Your Health",0.0327868852459),
			new Triple("Post Nation","Europe",0.0491803278689),
			new Triple("Wonkblog","Early Lead",0.00207468879668),
			new Triple("She The People","She The People",0.0526315789474),
			new Triple("Asia & Pacific","National Security",0.0344827586207),
			new Triple("National Security","World",0.122699386503),
			new Triple("Gridlock","The Fix",0.0322580645161),
			new Triple("Erik Wemple","On Leadership",0.0294117647059),
			new Triple("The Switch","On Small Business",0.0333333333333),
			new Triple("GovBeat","Opinions",0.047619047619),
			new Triple("Morning Mix","Post Nation",0.0109289617486),
			new Triple("Capital Weather Gang","World",0.144329896907),
			new Triple("Wonkblog","Capital Business",0.00622406639004),
			new Triple("Morning Mix","GovBeat",0.00546448087432),
			new Triple("Style","Erik Wemple",0.0588235294118),
			new Triple("Animalia","Achenblog",0.0140845070423),
			new Triple("WorldViews","Gridlock",0.00621118012422),
			new Triple("Erik Wemple","Arts and Entertainment",0.0294117647059),
			new Triple("National Security","Politics",0.0245398773006),
			new Triple("Wonkblog","Public Safety",0.0103734439834),
			new Triple("Style","Capital Business",0.0294117647059),
			new Triple("Asia & Pacific","The Fix",0.0344827586207),
			new Triple("Wonkblog","Travel",0.00414937759336),
			new Triple("Gridlock","Letters to the Editor",0.0322580645161),
			new Triple("To Your Health","PowerPost",0.00819672131148),
			new Triple("Gridlock","Post Nation",0.0322580645161),
			new Triple("Post Nation","Politics",0.0491803278689),
			new Triple("Style","On Leadership",0.0294117647059),
			new Triple("Morning Mix","Maryland Politics",0.0437158469945),
			new Triple("Animalia","Public Safety",0.0140845070423),
			new Triple("WorldViews","On Leadership",0.055900621118),
			new Triple("Morning Mix","On Leadership",0.00546448087432),
			new Triple("Morning Mix","Soccer Insider",0.00546448087432),
			new Triple("Animalia","Travel",0.0281690140845),
			new Triple("Europe","Wonkblog",0.190909090909),
			new Triple("Wonkblog","WorldViews",0.00207468879668),
			new Triple("Morning Mix","Answer Sheet",0.00546448087432),
			new Triple("Capital Weather Gang","Local",0.0721649484536),
			new Triple("Animalia","WorldViews",0.0281690140845),
			new Triple("To Your Health","Early Lead",0.016393442623),
			new Triple("WorldViews","Tablet",0.00621118012422),
			new Triple("Style","Politics",0.0588235294118),
			new Triple("Post Nation","Wonkblog",0.016393442623),
			new Triple("Morning Mix","On Parenting",0.0109289617486),
			new Triple("Animalia","Arts and Entertainment",0.0140845070423),
			new Triple("Business","Politics",0.0677966101695),
			new Triple("The Switch","Home & Garden",0.0166666666667),
			new Triple("Europe","Local",0.0818181818182),
			new Triple("She The People","Wonkblog",0.19298245614),
			new Triple("Style","Magazine",0.0294117647059),
			new Triple("Wonkblog","Checkpoint",0.00207468879668),
			new Triple("GovBeat","Food",0.0238095238095),
			new Triple("Post Nation","Federal Insider",0.016393442623),
			new Triple("Politics","Style",0.0588235294118),
			new Triple("Speaking of Science","Morning Mix",0.0169491525424),
			new Triple("Style","Right Turn",0.0294117647059),
			new Triple("WorldViews","Letters to the Editor",0.0124223602484),
			new Triple("Wonkblog","Fiscal Cliff",0.00207468879668),
			new Triple("Morning Mix","blog",0.00546448087432),
			new Triple("Morning Mix","Education",0.0327868852459),
			new Triple("Wonkblog","Maryland",0.00207468879668),
			new Triple("Wonkblog","Get There",0.00829875518672),
			new Triple("To Your Health","Wellness",0.0409836065574),
			new Triple("Maryland Politics","The Fix",0.0869565217391),
			new Triple("Post Nation","GovBeat",0.016393442623),
			new Triple("The Americas","WorldViews",0.666666666667),
			new Triple("Speaking of Science","Business",0.0508474576271),
			new Triple("Maryland Politics","On Leadership",0.0434782608696),
			new Triple("GovBeat","The Post's View",0.0238095238095),
			new Triple("Health & Science","WorldViews",0.125),
			new Triple("Europe","Middle East",0.00909090909091),
			new Triple("Europe","Letters to the Editor",0.00909090909091),
			new Triple("To Your Health","Health & Science",0.139344262295),
			new Triple("Style","The Switch",0.0294117647059),
			new Triple("Capital Weather Gang","Perspective",0.020618556701),
			new Triple("Transportation","Gridlock",0.134020618557),
			new Triple("Business","Speaking of Science",0.0169491525424),
			new Triple("Wonkblog","Health & Science",0.0186721991701),
			new Triple("Post Nation","nan",0.016393442623),
			new Triple("Morning Mix","Gridlock",0.00546448087432),
			new Triple("Capital Weather Gang","Asia & Pacific",0.020618556701),
			new Triple("The Switch","Style",0.0166666666667),
			new Triple("Speaking of Science","Post Live",0.0169491525424),
			new Triple("Animalia","Capital Weather Gang",0.0140845070423),
			new Triple("Morning Mix","Wonkblog",0.0437158469945),
			new Triple("National Security","National Security",0.269938650307),
			new Triple("Business","Opinions",0.0338983050847),
			new Triple("Morning Mix","The Volokh Conspiracy",0.00546448087432),
			new Triple("Animalia","Health & Science",0.0281690140845),
			new Triple("WorldViews","Maryland Politics",0.0124223602484),
			new Triple("National Security","Post Nation",0.0736196319018),
			new Triple("Gridlock","Gridlock",0.161290322581),
			new Triple("The Switch","Grade Point",0.0166666666667),
			new Triple("Europe","World",0.0181818181818),
			new Triple("The Switch","Morning Mix",0.0666666666667),
			new Triple("WorldViews","Wonkblog",0.0186335403727),
			new Triple("Asia & Pacific","Monkey Cage",0.0344827586207),
			new Triple("Wonkblog","She The People",0.0103734439834),
			new Triple("Morning Mix","Politics",0.0218579234973),
			new Triple("WorldViews","GovBeat",0.00621118012422),
			new Triple("Wonkblog","Sports",0.00414937759336),
			new Triple("GovBeat","Perspective",0.0238095238095),
			new Triple("Europe","Business",0.118181818182),
			new Triple("Capital Weather Gang","Energy and Environment",0.020618556701),
			new Triple("To Your Health","The Posts View",0.00819672131148),
			new Triple("Wonkblog","Europe",0.00207468879668),
			new Triple("National Security","PostEverything",0.0122699386503),
			new Triple("Animalia","The Switch",0.0140845070423),
			new Triple("WorldViews","PowerPost",0.0248447204969),
			new Triple("Energy and Environment","The Post's View",0.0357142857143),
			new Triple("Morning Mix","Acts of Faith",0.00546448087432),
			new Triple("Wonkblog","Opinion",0.0228215767635),
			new Triple("Post Nation","Post Nation",0.0491803278689),
			new Triple("Business","Wonkblog",0.186440677966),
			new Triple("Wonkblog","Monkey Cage",0.00414937759336),
			new Triple("GovBeat","Rampage",0.047619047619),
			new Triple("Gridlock","In the Loop",0.0322580645161),
			new Triple("The Switch","Real Estate",0.0333333333333),
			new Triple("Wonkblog","The Plum Line",0.0871369294606),
			new Triple("The Switch","The Switch",0.183333333333),
			new Triple("WorldViews","WorldViews",0.223602484472),
			new Triple("Politics","Politics",0.352941176471),
			new Triple("Wonkblog","Post Politics",0.0539419087137),
			new Triple("Speaking of Science","KidsPost",0.0169491525424),
			new Triple("Europe","National",0.0818181818182),
			new Triple("Transportation","Innovations",0.0103092783505),
			new Triple("Morning Mix","Europe",0.00546448087432),
			new Triple("Speaking of Science","Opinions",0.0338983050847),
			new Triple("She The People","Business",0.140350877193),
			new Triple("She The People","D.C. Politics",0.0175438596491),
			new Triple("Post Nation","Museums",0.0655737704918),
			new Triple("Morning Mix","Speaking of Science",0.00546448087432),
			new Triple("Wonkblog","Test",0.00207468879668),
			new Triple("Capital Weather Gang","Capital Weather Gang",0.484536082474),
			new Triple("Business","KidsPost",0.0169491525424),
			new Triple("Business","Cars",0.0169491525424),
			new Triple("GovBeat","Opinion",0.0238095238095),
			new Triple("Gridlock","Europe",0.0322580645161),
			new Triple("Maryland Politics","Public Safety",0.0434782608696),
			new Triple("WorldViews","Middle East",0.0310559006211),
			new Triple("The Switch","Wonkblog",0.0166666666667),
			new Triple("National Security","PowerPost",0.0245398773006),
			new Triple("GovBeat","WashPost PR Blog",0.0238095238095),
			new Triple("To Your Health","Letters to the Editor",0.00819672131148),
			new Triple("Transportation","Local",0.0515463917526),
			new Triple("Energy and Environment","Energy and Environment",0.678571428571),
			new Triple("National Security","Europe",0.0122699386503),
			new Triple("Wonkblog","Politics",0.097510373444),
			new Triple("Wonkblog","PostEverything",0.0352697095436),
			new Triple("Health & Science","The Post's View",0.0625),
			new Triple("The Switch","Politics",0.0166666666667),
			new Triple("Erik Wemple","The Switch",0.264705882353),
			new Triple("Morning Mix","Transportation",0.0109289617486),
			new Triple("To Your Health","Food",0.016393442623),
			new Triple("Post Nation","WorldViews",0.016393442623),
			new Triple("Wonkblog","The Fix",0.0435684647303),
			new Triple("Post Nation","Post Politics",0.0655737704918),
			new Triple("Maryland Politics","Opinion",0.0869565217391),
			new Triple("Morning Mix","True Crime",0.00546448087432),
			new Triple("The Switch","Ask The Post",0.0166666666667),
			new Triple("Morning Mix","Early Lead",0.0109289617486),
			new Triple("Museums","Books",0.25),
			new Triple("Business","Capital Business",0.118644067797),
			new Triple("WorldViews","Analysis",0.0310559006211),
			new Triple("WorldViews","Monkey Cage",0.192546583851),
			new Triple("Style","Post Local",0.0294117647059),
			new Triple("Business","Business",0.152542372881),
			new Triple("GovBeat","She The People",0.0238095238095),
			new Triple("Wonkblog","KidsPost",0.00207468879668),
			new Triple("Animalia","Morning Mix",0.267605633803),
			new Triple("Wonkblog","Speaking of Science",0.00207468879668),
			new Triple("Erik Wemple","National Security",0.0294117647059),
			new Triple("The Switch","Dr. Gridlock",0.0166666666667),
			new Triple("Politics","Virginia Politics",0.0588235294118),
			new Triple("Capital Weather Gang","Transportation",0.0103092783505),
			new Triple("Morning Mix","The Switch",0.0546448087432),
			new Triple("Transportation","Transportation",0.298969072165),
			new Triple("Post Nation","Immigration",0.0491803278689),
			new Triple("Speaking of Science","Health & Science",0.186440677966),
			new Triple("Erik Wemple","World",0.0294117647059),
			new Triple("Europe","Europe",0.236363636364),
			new Triple("Transportation","The Post's View",0.0103092783505),
			new Triple("Animalia","Local",0.056338028169),
			new Triple("To Your Health","Wonkblog",0.311475409836),
			new Triple("The Switch","Travel",0.0666666666667),
			new Triple("She The People","Innovations",0.0175438596491),
			new Triple("Wonkblog","Storyline",0.00622406639004),
			new Triple("She The People","Grade Point",0.122807017544),
			new Triple("Wonkblog","nan",0.00207468879668),
			new Triple("Animalia","Early Lead",0.0422535211268),
			new Triple("Transportation","Business",0.278350515464),
			new Triple("Wonkblog","Education",0.00414937759336),
			new Triple("Wonkblog","GovBeat",0.0622406639004),
			new Triple("Transportation","D.C. Politics",0.0412371134021),
			new Triple("Transportation","WashPost PR Blog",0.020618556701),
			new Triple("Europe","PostPartisan",0.00909090909091),
			new Triple("Erik Wemple","The Volokh Conspiracy",0.0294117647059),
			new Triple("She The People","Opinions",0.0526315789474),
			new Triple("Morning Mix","Health & Science",0.103825136612),
			new Triple("Wonkblog","Virginia Politics",0.00207468879668),
			new Triple("Asia & Pacific","National",0.0344827586207),
			new Triple("The Switch","Capital Business",0.0333333333333),
			new Triple("Wonkblog","Post Local",0.00207468879668),
			new Triple("Morning Mix","World",0.016393442623),
			new Triple("Maryland Politics","Maryland Politics",0.130434782609),
			new Triple("Business","The Switch",0.118644067797),
			new Triple("National Security","Post Politics",0.0429447852761),
			new Triple("Maryland Politics","The Post's View",0.0434782608696),
			new Triple("Wonkblog","The Fed Page",0.00207468879668),
			new Triple("Wonkblog","Fact Checker",0.0165975103734),
			new Triple("National Security","Opinion",0.0184049079755),
			new Triple("Museums","Museums",0.5),
			new Triple("GovBeat","Local",0.0238095238095),
			new Triple("She The People","Letters to the Editor",0.0175438596491),
			new Triple("Wonkblog","Perspective",0.00207468879668),
			new Triple("Morning Mix","Opinion",0.00546448087432),
			new Triple("She The People","Answer Sheet",0.140350877193),
			new Triple("WorldViews","Post Politics",0.0248447204969),
			new Triple("Maryland Politics","KidsPost",0.0434782608696),
			new Triple("GovBeat","Politics",0.119047619048),
			new Triple("Gridlock","Federal Insider",0.0645161290323),
			new Triple("The Switch","Perspective",0.0166666666667),
			new Triple("Capital Weather Gang","Health & Science",0.0515463917526),
			new Triple("Europe","The Posts View",0.0181818181818),
			new Triple("Morning Mix","Technology",0.0218579234973),
			new Triple("Morning Mix","She The People",0.0273224043716),
			new Triple("Asia & Pacific","Opinions",0.0344827586207),
			new Triple("Erik Wemple","Erik Wemple",0.0588235294118),
			new Triple("To Your Health","PostEverything",0.016393442623),
			new Triple("Post Nation","Movies",0.016393442623),
			new Triple("Animalia","National",0.0281690140845),
			new Triple("Wonkblog","Going Out Guide",0.00207468879668),
			new Triple("Morning Mix","Local",0.0382513661202),
			new Triple("WorldViews","National Security",0.0124223602484),
			new Triple("The Switch","Investigations",0.0166666666667),
			new Triple("WorldViews","Opinion",0.00621118012422),
			new Triple("Post Nation","Magazine",0.016393442623),
			new Triple("Style","The Fix",0.0588235294118),
			new Triple("Morning Mix","D.C. Politics",0.016393442623),
			new Triple("Animalia","Speaking of Science",0.0704225352113),
			new Triple("Animalia","Animalia",0.225352112676),
			new Triple("Wonkblog","To Your Health",0.0103734439834),
			new Triple("Transportation","Morning Mix",0.0103092783505),
			new Triple("Europe","Education",0.00909090909091),
			new Triple("Asia & Pacific","Asia & Pacific",0.241379310345),
			new Triple("Post Nation","Public Safety",0.016393442623),
			new Triple("To Your Health","To Your Health",0.213114754098),
			new Triple("Capital Weather Gang","WorldViews",0.020618556701),
			new Triple("Post Nation","Monkey Cage",0.016393442623),
			new Triple("Asia & Pacific","The Post's View",0.0689655172414),
			new Triple("Transportation","Wonkblog",0.0515463917526),
			new Triple("Morning Mix","PostEverything",0.0218579234973),
			new Triple("Asia & Pacific","Wonkblog",0.137931034483),
			new Triple("Style","Business",0.176470588235),
			new Triple("Wonkblog","Federal Insider",0.00622406639004),
			new Triple("Maryland Politics","Retropolis",0.0434782608696),
			new Triple("Speaking of Science","Wonkblog",0.0338983050847),
			new Triple("Animalia","Energy and Environment",0.0281690140845),
			new Triple("Morning Mix","The Post's View",0.016393442623),
			new Triple("Gridlock","Checkpoint",0.0967741935484),
			new Triple("She The People","Federal Insider",0.0175438596491),
			new Triple("Morning Mix","National",0.0327868852459),
			new Triple("Business","Innovations",0.0847457627119),
			new Triple("Animalia","Post Nation",0.0281690140845),
			new Triple("Wonkblog","Maryland Politics",0.0352697095436),
			new Triple("Animalia","Politics",0.0140845070423),
			new Triple("Europe","WorldViews",0.0272727272727),
			new Triple("Speaking of Science","Opinion",0.0338983050847),
			new Triple("National Security","National",0.0122699386503),
			new Triple("Gridlock","PowerPost",0.0322580645161),
			new Triple("Style","Wonkblog",0.117647058824),
			new Triple("Health & Science","Asia & Pacific",0.25),
			new Triple("Wonkblog","In Theory",0.00207468879668),
			new Triple("Post Nation","Morning Mix",0.0655737704918),
				
		}; 
	
	double getScore(String a,String b) {
		
		boolean firstFound = false;
		boolean secondFound = false;
		double min = 9999;
		
		for(Triple t : TABLE) {
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
			return 1;
	}
	public static void main(String[] args) {
		for(Triple t : TABLE) {
			System.out.println(t.first+t.second+t.value);
		}

	}

}
