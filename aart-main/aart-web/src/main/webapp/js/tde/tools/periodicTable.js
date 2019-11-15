var periodicTable = new function() {
	// var Properties = new Array("Name", "Symbol", "Atomic Number", "Atomic
	// Mass", "Group Number", "Period", "Block");
	
	// FIXME: This table should have all data we are not using removed

	var Table = new Array(106);
	Table["H"] = new Array(1, 1, 1.0079, 0.0899, 1, "1s1", "Gas", 14.025,
			20.268, 0.44936, 0.05868, 14.304, "1 (IA)", "", "Hexagonal", 14.4,
			0.79, 0.32, 0.001815, "", 13.598, 2.2, "Amphoteric", "Hydrogen",
			"H", 1, "s");
	Table["He"] = new Array(2, 4, 4.0026, 0.1787, "", "1s2", "Gas", 0.95,
			4.215, 0.0845, "", 5.193, "18 (VIIIA)", "Noble Gas", "Hexagonal",
			"", 0.49, 0.93, 0.00152, "", 24.587, "", "", "Helium", "He", 1, "s");
	Table["Li"] = new Array(3, 7, 6.941, 0.53, 1, "1s2&middot;2s1", "Solid",
			453.7, 1615, 145.92, 3, 3.6, "1 (IA)", "Alkali Metal", "Bcc", 13.1,
			2.05, 1.23, 0.847, 0.108, 5.392, 0.98, "Strong Base", "Lithium",
			"Li", 2,"s");
	Table["Be"] = new Array(4, 9, 9.01218, 1.85, 2, "1s2&middot;2s2", "Solid",
			1560, 2745, 292.4, 12.2, 1.82, "2 (IIA)", "Alkali Earth Metal",
			"Hexagonal", 5, 1.4, 0.9, 2, 0.313, 9.322, 1.57, "Amphoteric",
			"Beryllium", "Be", 2, "s");
	Table["B"] = new Array(5, 11, 10.81, 2.34, 3, "1s2&middot;2s2&middot;2p1",
			"Solid", 2300, 4275, 489.7, 50.2, 1.02, "13 (IIIA)", "",
			"Rhombohedral", 4.6, 1.17, 0.82, 0.27, 0.000000000001, 8.298, 2.04,
			"Mild Acid", "Boron", "B", 2, "p");
	Table["C"] = new Array(6, 12, 12.011, 2.62, "-4; 4; 2",
			"1s2&middot;2s2&middot;2p2", "Solid", 4100, 4470, 355.8, "", 0.71,
			"14 (IVA)", "", "Hexagonal", 4.58, 0.91, 0.77, 1.29, 0.00061,
			11.26, 2.55, "Mild Acid", "Carbon", "C", 2, "p");
	Table["N"] = new Array(7, 14, 14.0067, 1.251, "-3; 3; 5; 4; 2",
			"1s2&middot;2s2&middot;2p3", "Gas", 63.14, 77.35, 2.7928, 0.3604,
			1.04, "15 (VA)", "", "Hexagonal", 17.3, 0.75, 0.75, 0.0002598, "",
			14.534, 3.04, "Strong Acid", "Nitrogen", "N", 2, "p");
	Table["O"] = new Array(8, 16, 15.9994, 1.429, "-2",
			"1s2&middot;2s2&middot;2p4", "Gas", 50.35, 90.18, 3.4099, 0.22259,
			0.92, "16 (VIA)", "Chalcogen", "Cubic", 14, 0.65, 0.73, 0.0002674,
			"", 13.618, 3.44, "Neutral", "Oxygen", "O", 2, "p");
	Table["F"] = new Array(9, 19, 18.998403, 1.696, "-1",
			"1s2&middot;2s2&middot;2p5", "Gas", 53.48, 84.95, 3.2698, 0.2552,
			0.82, "17 (VIIA)", "Halogen", "Cubic", 17.1, 0.57, 0.72, 0.000279,
			"", 17.422, 3.98, "Strong Acid", "Fluorine", "F", 2, "p");
	Table["Ne"] = new Array(10, 20, 20.179, 0.901, "",
			"1s2&middot;2s2&middot;2p6", "Gas", 24.553, 27.096, 1.7326, 0.3317,
			0.904, "18 (VIIIA)", "Noble Gas", "Fcc", 16.7, 0.51, 0.71,
			0.000493, "", 21.564, "", "", "Neon", "Ne", 2, "p");
	Table["Na"] = new Array(11, 23, 22.98977, 0.97, 1, "[Ne]3s1", "Solid", 371,
			1156, 96.96, 2.598, 1.23, "1 (IA)", "Alkali Metal", "Bcc", 23.7,
			2.23, 1.54, 1.41, 0.21, 5.139, 0.93, "Strong Base", "Sodium", "Na",
			3, "s");
	Table["Mg"] = new Array(12, 24, 24.305, 1.74, 2, "[Ne]3s2", "Solid", 922,
			1363, 127.4, 8.954, 1.02, "2 (IIA)", "Alkali Earth Metal",
			"Hexagonal", 13.97, 1.72, 1.36, 1.56, 0.226, 7.646, 1.31,
			"Strong Base", "Magnesium", "Mg", 3, "s");
	Table["Al"] = new Array(13, 27, 26.98154, 2.7, 3, "[Ne]3s2&middot;3p1",
			"Solid", 933.25, 2740, 293.4, 10.79, 0.9, "13 (IIIA)", "", "Fcc",
			10, 1.82, 1.18, 2.37, 0.377, 5.986, 1.61, "Amphoteric", "Aluminum",
			"Al", 3, "p");
	Table["Si"] = new Array(14, 28, 28.0855, 2.33, 4, "[Ne]3s2&middot;3p2",
			"Solid", 1685, 3540, 384.22, 50.55, 0.71, "14 (IVA)", "", "Fcc",
			12.1, 1.46, 1.11, 1.48, "2.52E-12", 8.151, 1.9, "Amphoteric",
			"Silicon", "Si", 3, "p");
	Table["P"] = new Array(15, 31, 30.97376, 1.82, "5; 3; -3; 4",
			"[Ne]3s2&middot;3p3", "Solid", 317.3, 550, 12.129, 0.657, 0.77,
			"15 (VA)", "", "Monoclinic", 17, 1.23, 1.06, 0.00235, "1.E-17",
			10.486, 2.19, "Mild Acid", "Phosphorus", "P", 3, "p");
	Table["S"] = new Array(16, 32, 32.06, 2.07, "6; 2; -2; 4",
			"[Ne]3s2&middot;3p4", "Solid", 388.36, 717.75, "", 1.7175, 0.71,
			"16 (VIA)", "Chalcogen", "Orthorhombic", 15.5, 1.09, 1.02, 0.00269,
			"5.E-24", 10.36, 2.58, "Strong Acid", "Sulfur", "S", 3, "p");
	Table["Cl"] = new Array(17, 35, 35.453, 3.17, "-1; 1; 3; 5; 7",
			"[Ne]3s2&middot;3p5", "Gas", 172.16, 239.1, 10.2, 3.203, 0.48,
			"17 (VIIA)", "Halogen", "Orthorhombic", 22.7, 0.97, 0.99, 0.000089,
			"", 12.967, 3.16, "Strong Acid", "Chlorine", "Cl", 3, "p");
	Table["Ar"] = new Array(18, 40, 39.948, 1.784, "", "[Ne]3s2&middot;3p6",
			"Gas", 83.81, 87.3, 6.447, 1.188, 0.52, "18 (VIIIA)", "Noble Gas",
			"Fcc", 28.5, 0.88, 0.98, 0.0001772, "", 15.759, "", "", "Argon",
			"Ar", 3, "p");
	Table["K"] = new Array(19, 39, 39.0983, 0.86, 1, "[Ar]4s1", "Solid",
			336.35, 1032, 79.87, 2.334, 0.75, "1 (IA)", "Alkali Metal", "Bcc",
			45.46, 2.77, 2.03, 1.024, 0.139, 4.341, 0.82, "Strong Base",
			"Potassium", "K", 4, "s");
	Table["Ca"] = new Array(20, 40, 40.08, 1.55, 2, "[Ar]4s2", "Solid", 1112,
			1757, 153.6, 8.54, 0.63, "2 (IIA)", "Alkali Earth Metal", "Fcc",
			25.9, 2.23, 1.74, 2, 0.298, 6.113, 1, "Strong Base", "Calcium",
			"Ca", 4, "s");
	Table["Sc"] = new Array(21, 45, 44.9559, 3, 3, "[Ar]3d1&middot;4s2",
			"Solid", 1812, 3104, 314.2, 14.1, 0.6, "3 (IIIB)",
			"Transition Metal", "Hexagonal", 15, 2.09, 1.44, 0.158, 0.0177,
			6.54, 1.36, "Weak Base", "Scandium", "Sc", 4, "d");
	Table["Ti"] = new Array(22, 48, 47.9, 4.5, "4; 3", "[Ar]3d2&middot;4s2",
			"Solid", 1943, 3562, 421, 15.45, 0.52, "4 (IVB)",
			"Transition Metal", "Hexagonal", 10.64, 2, 1.32, 0.219, 0.0234,
			6.82, 1.54, "Amphoteric", "Titanium", "Ti", 4, "d");
	Table["V"] = new Array(23, 51, 50.9415, 5.8, "5; 4; 3; 2",
			"[Ar]3d3&middot;4s2", "Solid", 2175, 3682, 447.02, 20.9, 0.49,
			"5 (VB)", "Transition Metal", "Bcc", 8.78, 1.92, 1.22, 0.307,
			0.0489, 6.74, 1.63, "Amphoteric", "Vanadium", "V", 4, "d");
	Table["Cr"] = new Array(24, 52, 51.996, 7.19, "3; 6; 2",
			"[Ar]3d5&middot;4s1", "Solid", 2130, 2945, 344.3, 16.9, 0.45,
			"6 (VIB)", "Transition Metal", "Bcc", 7.23, 1.85, 1.18, 0.937,
			0.0774, 6.766, 1.66, "Strong Acid", "Chromium", "Cr", 4, "d");
	Table["Mn"] = new Array(25, 55, 54.938, 7.43, "2; 7; 6; 4; 3",
			"[Ar]3d5&middot;4s2", "Solid", 1517, 2335, 226, 12.05, 0.48,
			"7 (VIIB)", "Transition Metal", "Bcc", 7.39, 1.79, 1.17, 0.0782,
			0.00695, 7.435, 1.55, "Strong Acid", "Manganese", "Mn", 4, "d");
	Table["Fe"] = new Array(26, 56, 55.847, 7.86, "3; 2", "[Ar]3d6&middot;4s2",
			"Solid", 1809, 3135, 349.6, 13.8, 0.44, "8 (VIII)",
			"Transition Metal", "Bcc", 7.1, 1.72, 1.17, 0.802, 0.0993, 7.87,
			1.83, "Amphoteric", "Iron", "Fe", 4, "d");
	Table["Co"] = new Array(27, 59, 58.9332, 8.9, "2; 3", "[Ar]3d7&middot;4s2",
			"Solid", 1768, 3201, 376.5, 16.19, 0.42, "9 (VIII)",
			"Transition Metal", "Hexagonal", 6.7, 1.67, 1.16, 1, 0.172, 7.86,
			1.88, "Amphoteric", "Cobalt", "Co", 4, "d");
	Table["Ni"] = new Array(28, 58, 58.7, 8.9, "2; 3", "[Ar]3d8&middot;4s2",
			"Solid", 1726, 3187, 370.4, 17.47, 0.44, "10 (VIII)",
			"Transition Metal", "Fcc", 6.59, 1.62, 1.15, 0.907, 0.143, 7.635,
			1.91, "Mild Base", "Nickel", "Ni", 4, "d");
	Table["Cu"] = new Array(29, 63, 63.546, 8.96, "2; 1",
			"[Ar]3d10&middot;4s1", "Solid", 1357.6, 2836, 300.3, 13.05, 0.38,
			"11 (IB)", "Transition Metal", "Fcc", 7.1, 1.57, 1.17, 4.01, 0.596,
			7.726, 1.9, "Mild Base", "Copper", "Cu", 4, "d");
	Table["Zn"] = new Array(30, 64, 65.38, 7.14, 2, "[Ar]3d10&middot;4s2",
			"Solid", 692.73, 1180, 115.3, 7.322, 0.39, "12 (IIB)",
			"Transition Metal", "Hexagonal", 9.2, 1.53, 1.25, 1.16, 0.166,
			9.394, 1.65, "Amphoteric", "Zinc", "Zn", 4, "d");
	Table["Ga"] = new Array(31, 69, 69.72, 5.91, 3,
			"[Ar]3d10&middot;4s2&middot;4p1", "Liquid", 302.9, 2478, 258.7,
			5.59, 0.37, "13 (IIIA)", "", "Orthorhombic", 11.8, 1.81, 1.26,
			0.406, 0.0678, 5.999, 1.81, "Amphoteric", "Gallium", "Ga", 4, "p");
	Table["Ge"] = new Array(32, 74, 72.59, 5.32, 4,
			"[Ar]3d10&middot;4s2&middot;4p2", "Solid", 1210.4, 3107, 330.9,
			36.94, 0.32, "14 (IVA)", "", "Fcc", 13.6, 1.52, 1.22, 0.599,
			0.0000000145, 7.899, 2.01, "Amphoteric", "Germanium", "Ge", 4, "p");
	Table["As"] = new Array(33, 75, 74.9216, 5.72, "-3; 3; 5",
			"[Ar]3d10&middot;4s2&middot;4p3", "Solid", 1091, 886, 34.76, "",
			0.33, "15 (VA)", "", "Rhombohedral", 13.1, 1.33, 1.2, 0.5, 0.0345,
			9.81, 2.18, "Mild Acid", "Arsenic", "As", 4, "p");
	Table["Se"] = new Array(34, 80, 78.96, 4.8, "4; -2; 6",
			"[Ar]3d10&middot;4s2&middot;4p4", "Solid", 494, 958, 37.7, 6.694,
			0.32, "16 (VIA)", "Chalcogen", "Hexagonal", 16.45, 1.22, 1.16,
			0.0204, 0.000000000001, 9.752, 2.55, "Strong Acid", "Selenium",
			"Se", 4, "p");
	Table["Br"] = new Array(35, 79, 79.904, 3.12, "-1; 1; 5",
			"[Ar]3d10&middot;4s2&middot;4p5", "Liquid", 265.9, 332.25, 15.438,
			5.286, 0.473, "17 (VIIA)", "Halogen", "Orthorhombic", 23.5, 1.12,
			1.14, 0.00122, "", 11.814, 2.96, "Strong Acid", "Bromine", "Br", 4, "p");
	Table["Kr"] = new Array(36, 84, 83.8, 3.74, "",
			"[Ar]3d10&middot;4s2&middot;4p6", "Gas", 115.78, 119.8, 9.029,
			1.638, 0.248, "18 (VIIIA)", "Noble Gas", "Fcc", 38.9, 1.03, 1.12,
			0.0000949, "", 13.999, "", "", "Krypton", "Kr", 4, "p");
	Table["Rb"] = new Array(37, 85, 85.4678, 1.53, 1, "[Kr]5s1", "Solid",
			312.64, 961, 72.216, 2.192, 0.363, "1 (IA)", "Alkali Metal", "Bcc",
			55.9, 2.98, 2.16, 0.582, 0.0779, 4.177, 0.82, "Strong Base",
			"Rubidium", "Rb", 5, "s");
	Table["Sr"] = new Array(38, 88, 87.62, 2.6, 2, "[Kr]5s2", "Solid", 1041,
			1650, 144, 8.3, 0.3, "2 (IIA)", "Alkali Earth Metal", "Fcc", 33.7,
			2.45, 1.91, 0.353, 0.0762, 5.695, 0.95, "Strong Base", "Strontium",
			"Sr", 5, "s");
	Table["Y"] = new Array(39, 89, 88.9059, 4.5, 3, "[Kr]4d1&middot;5s2",
			"Solid", 1799, 3611, 363, 11.4, 0.3, "3 (IIIB)",
			"Transition Metal", "Hexagonal", 19.8, 2.27, 1.62, 0.172, 0.0166,
			6.38, 1.22, "Weak Base", "Yttrium", "Y", 5, "d");
	Table["Zr"] = new Array(40, 90, 91.22, 6.49, 4, "[Kr]4d2&middot;5s2",
			"Solid", 2125, 4682, 590.88, 16.9, 0.27, "4 (IVB)",
			"Transition Metal", "Hexagonal", 14.1, 2.16, 1.45, 0.227, 0.0236,
			6.84, 1.33, "Amphoteric", "Zirconium", "Zr", 5, "d");
	Table["Nb"] = new Array(41, 93, 92.9064, 8.55, "5; 3",
			"[Kr]4d4&middot;5s1", "Solid", 2740, 5017, 682, 26.4, 0.26,
			"5 (VB)", "Transition Metal", "Bcc", 10.87, 2.08, 1.34, 0.537,
			0.0693, 6.88, 1.6, "Mild Acid", "Niobium", "Nb", 5, "d");
	Table["Mo"] = new Array(42, 98, 95.94, 10.2, "6; 5; 4; 3; 2",
			"[Kr]4d5&middot;5s1", "Solid", 2890, 4912, 598, 32, 0.25,
			"6 (VIB)", "Transition Metal", "Bcc", 9.4, 2.01, 1.3, 1.38, 0.187,
			7.099, 2.16, "Strong Acid", "Molybdenum", "Mo", 5, "d");
	Table["Tc"] = new Array(43, 98, 98, 11.5, 7, "[Kr]4d5&middot;5s2",
			"Synthetic", 2473, 4538, 660, 24, 0.21, "7 (VIIB)",
			"Transition Metal", "Hexagonal", 8.5, 1.95, 1.27, 0.506, 0.067,
			7.28, 1.9, "Strong Acid", "Technetium", "Tc", 5, "d");
	Table["Ru"] = new Array(44, 102, 101.07, 12.2, "3; 4; 2; 6; 8",
			"[Kr]4d7&middot;5s1", "Solid", 2523, 4423, 595, 24, 0.238,
			"8 (VIII)", "Transition Metal", "Hexagonal", 8.3, 1.89, 1.25, 1.17,
			0.137, 7.37, 2.2, "Mild Acid", "Ruthenium", "Ru", 5, "d");
	Table["Rh"] = new Array(45, 103, 102.9055, 12.4, "3; 2; 4",
			"[Kr]4d8&middot;5s1", "Solid", 2236, 3970, 493, 21.5, 0.242,
			"9 (VIII)", "Transition Metal", "Fcc", 8.3, 1.83, 1.25, 1.5, 0.211,
			7.46, 2.28, "Amphoteric", "Rhodium", "Rh", 5, "d");
	Table["Pd"] = new Array(46, 106, 106.4, 12, "2; 4", "[Kr]4d10", "Solid",
			1825, 3237, 357, 17.6, 0.24, "10 (VIII)", "Transition Metal",
			"Fcc", 8.9, 1.79, 1.28, 0.718, 0.095, 8.34, 2.2, "Mild Base",
			"Palladium", "Pd", 5, "d");
	Table["Ag"] = new Array(47, 107, 107.868, 10.5, 1, "[Kr]4d10&middot;5s1",
			"Solid", 1234, 2436, 250.58, 11.3, 0.235, "11 (IB)",
			"Transition Metal", "Fcc", 10.3, 1.75, 1.34, 4.29, 0.63, 7.576,
			1.93, "Amphoteric", "Silver", "Ag", 5, "d");
	Table["Cd"] = new Array(48, 114, 112.41, 8.65, 2, "[Kr]4d10&middot;5s2",
			"Solid", 594.18, 1040, 99.57, 6.192, 0.23, "12 (IIB)",
			"Transition Metal", "Hexagonal", 13.1, 1.71, 1.48, 0.968, 0.138,
			8.993, 1.69, "Mild Base", "Cadmium", "Cd", 5, "d");
	Table["In"] = new Array(49, 115, 114.82, 7.31, 3,
			"[Kr]4d10&middot;5s2&middot;5p1", "Solid", 429.76, 2346, 231.5,
			3.263, 0.23, "13 (IIIA)", "", "Tetragonal", 15.7, 2, 1.44, 0.816,
			0.116, 5.786, 1.78, "Amphoteric", "Indium", "In", 5, "p");
	Table["Sn"] = new Array(50, 120, 118.69, 7.3, "4; 2",
			"[Kr]4d10&middot;5s2&middot;5p2", "Solid", 505.06, 2876, 295.8,
			7.029, 0.227, "14 (IVA)", "", "Tetragonal", 16.3, 1.72, 1.41,
			0.666, 0.0917, 7.344, 1.96, "Amphoteric", "Tin", "Sn", 5, "p");
	Table["Sb"] = new Array(51, 121, 121.75, 6.68, "-3; 3; 5",
			"[Kr]4d10&middot;5s2&middot;5p3", "Solid", 904, 1860, 77.14, 19.87,
			0.21, "15 (VA)", "", "Rhombohedral", 18.23, 1.53, 1.4, 0.243,
			0.0288, 8.641, 2.05, "Mild Acid", "Antimony", "Sb", 5, "p");
	Table["Te"] = new Array(52, 130, 127.6, 6.24, "4; 2; 5; 6",
			"[Kr]4d10&middot;5s2&middot;5p4", "Solid", 722.65, 1261, 52.55,
			17.49, 0.2, "16 (VIA)", "Chalcogen", "Hexagonal", 20.5, 1.42, 1.36,
			0.0235, 0.000002, 9.009, 2.1, "Mild Acid", "Tellurium", "Te", 5, "p");
	Table["I"] = new Array(53, 127, 126.9045, 4.92, "-1; 1; 5; 7",
			"[Kr]4d10&middot;5s2&middot;5p5", "Solid", 386.7, 458.4, 20.752,
			7.824, 0.214, "17 (VIIA)", "Halogen", "Orthorhombic", 25.74, 1.32,
			1.33, 0.00449, "8.E-16", 10.451, 2.66, "Strong Acid", "Iodine",
			"I", 5, "p");
	Table["Xe"] = new Array(54, 132, 131.3, 5.89, "",
			"[Kr]4d10&middot;5s2&middot;5p6", "Gas", 161.36, 165.03, 12.636,
			2.297, 0.158, "18 (VIIIA)", "Noble Gas", "Fcc", 37.3, 1.24, 1.31,
			0.0000569, "", 12.13, "", "Weak Acid", "Xenon", "Xe", 5, "p");
	Table["Cs"] = new Array(55, 133, 132.9054, 1.87, 1, "[Xe]6s1", "Liquid",
			301.55, 944, 67.74, 2.092, 0.24, "1 (IA)", "Alkali Metal", "Bcc",
			71.07, 3.34, 2.35, 0.359, 0.0489, 3.894, 0.79, "Strong Base",
			"Cesium", "Cs", 6, "s");
	Table["Ba"] = new Array(56, 138, 137.33, 3.5, 2, "[Xe]6s2", "Solid", 1002,
			2171, 142, 7.75, 0.204, "2 (IIA)", "Alkali Earth Metal", "Bcc",
			39.24, 2.78, 1.98, 0.184, 0.03, 5.212, 0.89, "Strong Base",
			"Barium", "Ba", 6, "s");
	Table["La"] = new Array(57, 139, 138.9055, 6.7, 3, "[Xe]5d1&middot;6s2",
			"Solid", 1193, 3730, 414, 6.2, 0.19, "3 (IIIB)",
			"Transition Metal", "Hexagonal", 20.73, 2.74, 1.69, 0.135, 0.0126,
			5.58, 1.1, "Strong Base", "Lanthanum", "La", 6, "f");
	Table["Ce"] = new Array(58, 140, 140.12, 6.78, "3; 4",
			"[Xe]4f1&middot;5d1&middot;6s2", "Solid", 1071, 3699, 414, 5.46,
			0.19, "", "Lanthanide", "Fcc", 20.67, 2.7, 1.65, 0.114, 0.0115,
			5.54, 1.12, "Mild Base", "Cerium", "Ce", 6, "f");
	Table["Pr"] = new Array(59, 141, 140.9077, 6.77, "3; 4",
			"[Xe]4f3&middot;6s2", "Solid", 1204, 3785, 296.8, 6.89, 0.19, "",
			"Lanthanide", "Hexagonal", 20.8, 2.67, 1.65, 0.125, 0.0148, 5.46,
			1.13, "Mild Base", "Praseodymium", "Pr", 6, "f");
	Table["Nd"] = new Array(60, 142, 144.24, 7, 3, "[Xe]4f4&middot;6s2",
			"Solid", 1289, 3341, 273, 7.14, 0.19, "", "Lanthanide",
			"Hexagonal", 20.6, 2.64, 1.64, 0.165, 0.0157, 5.53, 1.14,
			"Mild Base", "Neodymium", "Nd", 6, "f");
	Table["Pm"] = new Array(61, 145, 145, 6.475, 3, "[Xe]4f5&middot;6s2",
			"Synthetic", 1441, 2733, "", "", "", "", "Lanthanide", "Hexagonal",
			22.39, 2.62, 1.63, 0.179, "", 5.554, 1.13, "Mild Base",
			"Promethium", "Pm", 6, "f");
	Table["Sm"] = new Array(62, 152, 150.4, 7.54, "3; 2", "[Xe]4f6&middot;6s2",
			"Solid", 1345, 2064, 166.4, 8.63, 0.2, "", "Lanthanide",
			"Rhombohedral", 19.95, 2.59, 1.62, 0.133, 0.00956, 5.64, 1.17,
			"Mild Base", "Samarium", "Sm", 6, "f");
	Table["Eu"] = new Array(63, 153, 151.96, 5.26, "3; 2",
			"[Xe]4f7&middot;6s2", "Solid", 1090, 1870, 143.5, 9.21, 0.18, "",
			"Lanthanide", "Bcc", 28.9, 2.56, 1.85, 0.139, 0.0112, 5.67, 1.2,
			"Mild Base", "Europium", "Eu", 6, "f");
	Table["Gd"] = new Array(64, 158, 157.25, 7.89, 3,
			"[Xe]4f7&middot;5d1&middot;6s2", "Solid", 1585, 3539, 359.4, 10.05,
			0.23, "", "Lanthanide", "Hexagonal", 19.9, 2.54, 1.61, 0.106,
			0.00736, 6.15, 1.2, "Mild Base", "Gadolinium", "Gd", 6, "f");
	Table["Tb"] = new Array(65, 159, 158.9254, 8.27, "3; 4",
			"[Xe]4f9&middot;6s2", "Solid", 1630, 3496, 330.9, 10.8, 0.18, "",
			"Lanthanide", "Hexagonal", 19.2, 2.51, 1.59, 0.111, 0.00889, 5.86,
			1.2, "Weak Base", "Terbium", "Tb", 6, "f");
	Table["Dy"] = new Array(66, 164, 162.5, 8.54, 3, "[Xe]4f10&middot;6s2",
			"Solid", 1682, 2835, 230, 11.06, 0.17, "", "Lanthanide",
			"Hexagonal", 19, 2.49, 1.59, 0.107, 0.0108, 5.94, 1.22,
			"Weak Base", "Dysprosium", "Dy", 6, "f");
	Table["Ho"] = new Array(67, 165, 164.9304, 8.8, 3, "[Xe]4f11&middot;6s2",
			"Solid", 1743, 2968, 241, 12.2, 0.16, "", "Lanthanide",
			"Hexagonal", 18.7, 2.47, 1.58, 0.162, 0.0124, 6.018, 1.23,
			"Weak Base", "Holmium", "Ho", 6, "f");
	Table["Er"] = new Array(68, 166, 167.26, 9.05, 3, "[Xe]4f12&middot;6s2",
			"Solid", 1795, 3136, 261, 19.9, 0.17, "", "Lanthanide",
			"Hexagonal", 18.4, 2.45, 1.57, 0.143, 0.0117, 6.101, 1.24,
			"Weak Base", "Erbium", "Er", 6, "f");
	Table["Tm"] = new Array(69, 169, 168.9342, 9.33, "3; 2",
			"[Xe]4f13&middot;6s2", "Solid", 1818, 2220, 191, 16.84, 0.16, "",
			"Lanthanide", "Hexagonal", 18.1, 2.42, 1.56, 0.168, 0.015, 6.184,
			1.25, "Weak Base", "Thulium", "Tm", 6, "f");
	Table["Yb"] = new Array(70, 174, 173.04, 6.98, "3; 2",
			"[Xe]4f14&middot;6s2", "Solid", 1097, 1467, 128.9, 7.66, 0.15, "",
			"Lanthanide", "Fcc", 24.79, 2.4, 1.74, 0.349, 0.0351, 6.254, 1.1,
			"Weak Base", "Ytterbium", "Yb", 6, "f");
	Table["Lu"] = new Array(71, 175, 174.967, 9.84, 3,
			"[Xe]4f14&middot;5d1&middot;6s2", "Solid", 1936, 3668, 355.9, 18.6,
			0.15, "", "Lanthanide", "Hexagonal", 17.78, 2.25, 1.56, 0.164,
			0.0185, 5.43, 1.27, "Weak Base", "Lutetium", "Lu", 6, "f");
	Table["Hf"] = new Array(72, 180, 178.49, 13.1, 4,
			"[Xe]4f14&middot;5d2&middot;6s2", "Solid", 2500, 4876, 575, 24.06,
			0.14, "4 (IVB)", "Transition Metal", "Hexagonal", 13.6, 2.16, 1.44,
			0.23, 0.0312, 6.65, 1.3, "Amphoteric", "Hafnium", "Hf", 6, "d");
	Table["Ta"] = new Array(73, 181, 180.9479, 16.6, 5,
			"[Xe]4f14&middot;5d3&middot;6s2", "Solid", 3287, 5731, 743, 31.6,
			0.14, "5 (VB)", "Transition Metal", "Bcc", 10.9, 2.09, 1.34, 0.575,
			0.0761, 7.89, 1.5, "Mild Acid", "Tantalum", "Ta", 6, "d");
	Table["W"] = new Array(74, 184, 183.85, 19.3, "6; 5; 4; 3; 2",
			"[Xe]4f14&middot;5d4&middot;6s2", "Solid", 3680, 5828, 824, 35.4,
			0.13, "6 (VIB)", "Transition Metal", "Bcc", 9.53, 2.02, 1.3, 1.74,
			0.189, 7.98, 2.36, "Mild Acid", "Tungsten", "W", 6, "d");
	Table["Re"] = new Array(75, 187, 186.207, 21, "7; 6; 4; 2; -1",
			"[Xe]4f14&middot;5d5&middot;6s2", "Solid", 3453, 5869, 715, 33.2,
			0.13, "7 (VIIB)", "Transition Metal", "Hexagonal", 8.85, 1.97,
			1.28, 0.479, 0.0542, 7.88, 1.9, "Mild Acid", "Rhenium", "Re", 6, "d");
	Table["Os"] = new Array(76, 192, 190.2, 22.4, "4; 2; 3; 6; 8",
			"[Xe]4f14&middot;5d6&middot;6s2", "Solid", 3300, 5285, 746, 31.8,
			0.13, "8 (VIII)", "Transition Metal", "Hexagonal", 8.49, 1.92,
			1.26, 0.876, 0.109, 8.7, 2.2, "Mild Acid", "Osmium", "Os", 6, "d");
	Table["Ir"] = new Array(77, 193, 192.22, 22.5, "4; 2; 3; 6",
			"[Xe]4f14&middot;5d7&middot;6s2", "Solid", 2716, 4701, 604, 26.1,
			0.13, "9 (VIII)", "Transition Metal", "Fcc", 8.54, 1.87, 1.27,
			1.47, 0.197, 9.1, 2.2, "Mild Base", "Iridium", "Ir", 6, "d");
	Table["Pt"] = new Array(78, 195, 195.09, 21.4, "4; 2",
			"[Xe]4f14&middot;5d9&middot;6s1", "Solid", 2045, 4100, 510, 19.6,
			0.13, "10 (VIII)", "Transition Metal", "Fcc", 9.1, 1.83, 1.3,
			0.716, 0.0966, 9, 2.28, "Mild Base", "Platinum", "Pt", 6, "d");
	Table["Au"] = new Array(79, 197, 196.9665, 19.3, "3; 1",
			"[Xe]4f14&middot;5d10&middot;6s1", "Solid", 1337.58, 3130, 334.4,
			12.55, 0.128, "11 (IB)", "Transition Metal", "Fcc", 10.2, 1.79,
			1.34, 3.17, 0.452, 9.225, 2.54, "Amphoteric", "Gold", "Au", 6, "d");
	Table["Hg"] = new Array(80, 202, 200.59, 13.53, "2; 1",
			"[Xe]4f14&middot;5d10&middot;6s2", "Liquid", 234.28, 630, 59.229,
			2.295, 0.139, "12 (IIB)", "Transition Metal", "Rhombohedral",
			14.82, 1.76, 1.49, 0.0834, 0.0104, 10.437, 2, "Mild Base",
			"Mercury", "Hg", 6, "d");
	Table["Tl"] = new Array(81, 205, 204.37, 11.85, "1; 3",
			"[Xe]4f14&middot;5d10&middot;6s2&middot;6p1", "Solid", 577, 1746,
			164.1, 4.142, 0.13, "13 (IIIA)", "", "Hexagonal", 17.2, 2.08, 1.48,
			0.461, 0.0617, 6.108, 2.04, "Mild Base", "Thallium", "Tl", 6, "p");
	Table["Pb"] = new Array(82, 208, 207.2, 11.4, "2; 4",
			"[Xe]4f14&middot;5d10&middot;6s2&middot;6p2", "Solid", 600.6, 2023,
			177.7, 4.799, 0.13, "14 (IVA)", "", "Fcc", 18.17, 1.81, 1.47,
			0.353, 0.0481, 7.416, 2.33, "Amphoteric", "Lead", "Pb", 6, "p");
	Table["Bi"] = new Array(83, 209, 208.9804, 9.8, "3; 5",
			"[Xe]4f14&middot;5d10&middot;6s2&middot;6p3", "Solid", 544.52,
			1837, 104.8, 11.3, 0.12, "15 (VA)", "", "Rhombohedral", 21.3, 1.63,
			1.46, 0.0787, 0.00867, 7.289, 2.02, "Mild Acid", "Bismuth", "Bi", 6, "p");
	Table["Po"] = new Array(84, 209, 209, 9.4, "4; 2",
			"[Xe]4f14&middot;5d10&middot;6s2&middot;6p4", "Solid", 527, 1235,
			"", "", "", "16 (VIA)", "", "Monoclinic", 22.23, 1.53, 1.46, 0.2,
			0.0219, 8.42, 2, "Amphoteric", "Polonium", "Po", 6, "p");
	Table["At"] = new Array(85, 210, 210, "", "-1; 1; 3; 5; 7",
			"[Xe]4f14&middot;5d10&middot;6s2&middot;6p5", "Solid", 575, 610,
			"", "", "", "17 (VIIA)", "Halogen", "", "", 1.43, 1.45, 0.017, "",
			"", 2.2, "", "Astatine", "At", 6, "p");
	Table["Rn"] = new Array(86, 222, 222, 9.91, "",
			"[Xe]4f14&middot;5d10&middot;6s2&middot;6p6", "Gas", 202, 211,
			16.4, 2.89, 0.09, "18 (VIIIA)", "Noble Gas", "Fcc", 50.5, 1.34, "",
			0.0000364, "", 10.748, "", "", "Radon", "Rn", 6, "p");
	Table["Fr"] = new Array(87, 223, 223, "", 1, "[Rn]7s1", "Liquid", 300, 950,
			"", "", "", "1 (IA)", "Alkali Metal", "Bcc", "", "", "", 0.15,
			0.03, "", 0.7, "Strong Base", "Francium", "Fr", 7, "s");
	Table["Ra"] = new Array(88, 226, 226.0254, 5, 2, "[Rn]7s2", "Solid", 973,
			1809, "", "", "", "2 (IIA)", "Alkali Earth Metal", "Bcc", 45.2, "",
			"", 0.186, "", 5.279, 0.9, "Strong Base", "Radium", "Ra", 7, "s");
	Table["Ac"] = new Array(89, 227, 227.0278, 10.07, 3, "[Rn]6d1&middot;7s2",
			"Solid", 1323, 3473, "", "", "", "3 (IIIB)", "Transition Metal",
			"Fcc", 22.54, "", "", 0.12, "", 5.17, 1.1, "Neutral", "Actinium",
			"Ac", 7, "f");
	Table["Th"] = new Array(90, 232, 232.0381, 11.7, 4, "[Rn]6d2&middot;7s2",
			"Solid", 2028, 5061, 514.4, 16.1, 0.12, "", "Actinide", "Fcc",
			19.9, "", 1.65, 0.54, 0.0653, 6.08, 1.3, "Weak Base", "Thorium",
			"Th", 7, "f");
	Table["Pa"] = new Array(91, 231, 231.0359, 15.4, "5; 4",
			"[Rn]5f2&middot;6d1&middot;7s2", "Solid", "", "", "", 12.3, "", "",
			"Actinide", "Orthorhombic", 15, "", "", 0.47, 0.0529, 5.89, 1.5,
			"Weak Base", "Protactinium", "Pa", 7, "f");
	Table["U"] = new Array(92, 238, 238.029, 18.9, "6; 5; 4; 3",
			"[Rn]5f3&middot;6d1&middot;7s2", "Solid", 1405, 4407, 477, 8.52,
			0.12, "", "Actinide", "Orthorhombic", 12.59, "", 1.42, 0.276,
			0.038, 6.05, 1.38, "Amphoteric", "Uranium", "U", 7, "f");
	Table["Np"] = new Array(93, 237, 237.0482, 20.4, "5; 6; 4; 3",
			"[Rn]5f4&middot;6d1&middot;7s2", "Synthetic", 910, "", "", 5.19,
			0.12, "", "Actinide", "Orthorhombic", 11.62, "", "", 0.063,
			0.00822, 6.19, 1.36, "Amphoteric", "Neptunium", "Np", 7, "f");
	Table["Pu"] = new Array(94, 244, 244, 19.8, "4; 6; 5; 3",
			"[Rn]5f6&middot;7s2", "Synthetic", 913, 3503, 344, 2.84, 0.13, "",
			"Actinide", "Monoclinic", 12.32, "", "", 0.0674, 0.00666, 6.06,
			1.28, "Amphoteric", "Plutonium", "Pu", 7, "f");
	Table["Am"] = new Array(95, 243, 243, 13.6, "3; 6; 5; 4",
			"[Rn]5f7&middot;7s2", "Synthetic", 1268, 2880, "", 14.4, 0.11, "",
			"Actinide", "Hexagonal", 17.86, "", "", 0.1, 0.022, 5.993, 1.3,
			"Amphoteric", "Americium", "Am", 7, "f");
	Table["Cm"] = new Array(96, 247, 247, 13.511, 3,
			"[Rn]5f7&middot;6d1&middot;7s2", "Synthetic", 1613, "", "", 15, "",
			"", "Actinide", "", 18.28, "", "", 0.1, "", 6.02, 1.3,
			"Amphoteric", "Curium", "Cm", 7, "f");
	Table["Bk"] = new Array(97, 247, 247, "", "3; 4", "[Rn]5f9&middot;7s2",
			"Synthetic", "", "", "", "", "", "", "Actinide", "", "", "", "",
			0.1, "", 6.23, 1.3, "", "Berkelium", "Bk", 7, "f");
	Table["Cf"] = new Array(98, 251, 251, "", 3, "[Rn]5f10&middot;7s2",
			"Synthetic", 900, "", "", "", "", "", "Actinide", "", "", "", "",
			0.1, "", 6.3, 1.3, "", "Californium", "Cf", 7, "f");
	Table["Es"] = new Array(99, 252, 252, "", "", "[Rn]5f11&middot;7s2",
			"Synthetic", "", "", "", "", "", "", "Actinide", "", "", "", "",
			0.1, "", 6.42, 1.3, "", "Einsteinium", "Es", 7, "f");
	Table["Fm"] = new Array(100, 257, 257, "", "", "[Rn]5f12&middot;7s2",
			"Synthetic", "", "", "", "", "", "", "Actinide", "", "", "", "",
			0.1, "", 6.5, 1.3, "", "Fermium", "Fm", 7, "f");
	Table["Md"] = new Array(101, 258, 258, "", "", "[Rn]5f13&middot;7s2",
			"Synthetic", "", "", "", "", "", "", "Actinide", "", "", "", "",
			0.1, "", 6.58, 1.3, "", "Mendelevium", "Md", 7, "f");
	Table["No"] = new Array(102, 259, 259, "", "", "[Rn]5f14&middot;7s2",
			"Synthetic", "", "", "", "", "", "", "Actinide", "", "", "", "",
			0.1, "", 6.65, 1.3, "", "Nobelium", "No", 7, "f");
	Table["Lr"] = new Array(103, 260, 260, "", "",
			"[Rn]5f14&middot;6d1&middot;7s2", "Synthetic", "", "", "", "", "",
			"", "Actinide", "", "", "", "", 0.1, "", "", "", "", "Lawrencium",
			"Lr", 7, "f");
	Table["Rf"] = new Array(104, 261, 261, "", "",
			"[Rn]5f14&middot;6d2&middot;7s2", "Synthetic", "", "", "", "", "",
			"4 (IVB)", "Transition Metal", "", "", "", "", 0.23, "", "", "",
			"", "Rutherfordium", "Rf", 7, "d");
	Table["Db"] = new Array(105, 262, 262, "", "",
			"[Rn]5f14&middot;6d3&middot;7s2", "Synthetic", "", "", "", "", "",
			"5 (VB)", "Transition Metal", "", "", "", "", 0.58, "", "", "", "",
			"Dubnium", "Db", 7, "d");
	Table["Sg"] = new Array(106, 263, 263, "", "",
			"[Rn]5f14&middot;6d4&middot;7s2", "Synthetic", "", "", "", "", "",
			"6 (VIB)", "Transition Metal", "", "", "", "", "", "", "", "", "",
			"Seaborgium", "Sg", 7, "d");
	// dlm-added
	Table["Bh"] = new Array(107, 264, 264, "", "", "", "", "", "", "", "", "",
			"7 (VIIB)", "Transition Metal", "", "", "", "", "", "", "", "", "",
			"Bohrium", "Bh", 7, "d");
	Table["Hs"] = new Array(108, 269, 269, "", "", "", "", "", "", "", "", "",
			"8 (VIII)", "Transition Metal", "", "", "", "", "", "", "", "", "",
			"Hassium", "Hs", 7, "d");
	Table["Mt"] = new Array(109, "", "", "", "", "", "", "", "", "", "", "",
			"9 (VIII)", "Transition Metal", "", "", "", "", "", "", "", "", "",
			"Meitnerium", "Mt", 7, "d");
	Table["Ds"] = new Array(110, "", "", "", "", "", "", "", "", "", "", "",
			"10 (VIII)", "Transition Metal", "", "", "", "", "", "", "", "",
			"", "Darmstadtium", "Ds", 7, "d");
	Table["Rg"] = new Array(111, "", "", "", "", "", "", "", "", "", "", "",
			"11 (IB)", "Transition Metal", "", "", "", "", "", "", "", "", "",
			"Roentgenium", "Rg", 7, "d");
	Table["Cn"] = new Array(112, "", "", "", "", "", "", "", "", "", "", "",
			"12 (IIB)", "Transition Metal", "", "", "", "", "", "", "", "", "",
			"Copernicium", "Cn", 7, "d");
	Table["Uut"] = new Array(113, "", "", "", "", "", "", "", "", "", "", "",
			"13 (IIIA)", "Transition Metal", "", "", "", "", "", "", "", "",
			"", "Ununtrium", "Uut", 7, "p");
	Table["Uuq"] = new Array(114, "", "", "", "", "", "", "", "", "", "", "",
			"14 (IVA)", "Transition Metal", "", "", "", "", "", "", "", "", "",
			"Ununquadium", "Uuq", 7, "p");
	Table["Uup"] = new Array(115, "", "", "", "", "", "", "", "", "", "", "",
			"15 (VA)", "Transition Metal", "", "", "", "", "", "", "", "", "",
			"Ununpentium", "Uup", 7, "p");
	Table["Lv"] = new Array(116, "", 293, "", "", "", "", "", "", "", "", "",
			"16 (VIA)", "Transition Metal", "", "", "", "", "", "", "", "", "",
			"Livermorium", "Lv", 7, "p");
	Table["Uus"] = new Array(117, "", "", "", "", "", "", "", "", "", "", "",
			"17 (VIIA)", "Transition Metal", "", "", "", "", "", "", "", "",
			"", "Ununseptium", "Uus", 7, "p");
	Table["Uuo"] = new Array(118, "", "", "", "", "", "", "", "", "", "", "",
			"18 (VIIIA)", "Transition Metal", "", "", "", "", "", "", "", "",
			"", "Ununoctium", "Uuo", 7, "p");

	this.showElement = function(e) {
		$('#pTElem ul').html('');
		$('#pTElem h3').html(tool_names.periodicTable.ptname + ': '+ Table[e][23]);
		$('#pTElem ul').append('<li>'+tool_names.periodicTable.symbol + ': '+ Table[e][24]+'</li>');
		$('#pTElem ul').append('<li>'+tool_names.periodicTable.atomicnumber + ': '+ Table[e][0]+'</li>');
		$('#pTElem ul').append('<li>'+tool_names.periodicTable.atomicmass + ': '+ Table[e][2]+'</li>');
		$('#pTElem ul').append('<li>'+tool_names.periodicTable.groupnumber + ': '+ Table[e][12]+'</li>');
		$('#pTElem ul').append('<li>'+tool_names.periodicTable.period + ': '+ Table[e][25]+'</li>');
		$('#pTElem ul').append('<li>'+tool_names.periodicTable.block + ': '+ Table[e][26]+'</li>');
		if ($("#pTElem").is(':hidden')) {
			var pos = $('#pt').offset();
			$('#pTElem').css({
				"left" : (pos.left) + "px",
				"top" : (pos.top - 30) + "px"
			}).show();
		}	
	};

};