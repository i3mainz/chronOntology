package objconverter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Convert Europe XYZ-Grid to OBJ Facesets in a Layer Structure CC BY-SA 4.0
 *
 * @author Florian Thiery
 */
public class ObjLayerConverter {

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		// max 7 z-values: 0 water, 1 landscape, {values} area
		String input = Config.inputName;
		// properties
		Logger log = Logger.getLogger(ObjLayerConverter.class.getName());
		String input_name = input + ".xyz";
		String output_name = input + "_layer.obj";
		String input_path = Config.inputPath + input_name;
		String output_path = Config.outputPath + output_name;
		int B = 426; // bei europa_acc4 426 sonst 425
		double X1_SOLL = -1245000.0;
		double Y1_SOLL = 7995000.0;
		double BB_UPPER_LEFT_X = -1250000;
		double BB_UPPER_LEFT_Y = 8000000;
		int halfGridSize = 5000;
		int gridSize = halfGridSize * 2;
		int y_offset = 449;
		// Z dimensions
		int hight_plus = 20;
		int height_plus_half = (hight_plus / 2);
		int height_plus_double = (hight_plus * 7);
		// Europe Layer
		int z_water = 0;
		int z_landscape = z_water + hight_plus;
		// 1st Layer
		int z_area2_water = z_landscape + (height_plus_double);
		int z_area2_landscape = z_area2_water + (height_plus_half);
		int z_area2 = z_area2_landscape + hight_plus;
		// 2nd Layer
		int z_area3_water = z_area2 + (height_plus_double);
		int z_area3_landscape = z_area3_water + (height_plus_half);
		int z_area3 = z_area3_landscape + hight_plus;
		// 3rd Layer
		int z_area4_water = z_area3 + (height_plus_double);
		int z_area4_landscape = z_area4_water + (height_plus_half);
		int z_area4 = z_area4_landscape + hight_plus;
		// 4th Layer
		int z_area5_water = z_area4 + (height_plus_double);
		int z_area5_landscape = z_area5_water + (height_plus_half);
		int z_area5 = z_area5_landscape + hight_plus;
		// 5th Layer
		int z_area6_water = z_area5 + (height_plus_double);
		int z_area6_landscape = z_area6_water + (height_plus_half);
		int z_area6 = z_area6_landscape + hight_plus;
		// RGB
		double[] rgb_water = {0.0, 0.0, 0.0};
		double[] rgb_landscape = {0.71, 0.44, 0.27};
		double[] rgb_area = {0.90, 0.00, 0.00};
		// values
		int[] values = {2, 123, 244, 355, 466};
		double landscape_MIN = 0.0;
		double landscape_MAX = 10000.0;

		// start
		try {
			System.out.println("START ObjLayerConverter!");
			// read XYZ file
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(input_path), "UTF-8"));
			String line = "";
			int lauf = 0;
			double new_X = -42;
			double new_Y = -42;
			// init lists
			List<String> VERTEX_LIST_ORIG = new ArrayList<String>();
			List<String> FACESET_LIST = new ArrayList<String>();
			List<String> VERTEX_LIST_WATER = new ArrayList<String>();
			List<String> VERTEX_LIST_LANDSCAPE = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA2_WATER = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA2_LANDSCAPE = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA2 = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA3_WATER = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA3_LANDSCAPE = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA3 = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA4_WATER = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA4_LANDSCAPE = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA4 = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA5_WATER = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA5_LANDSCAPE = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA5 = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA6_WATER = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA6_LANDSCAPE = new ArrayList<String>();
			List<String> VERTEX_LIST_AREA6 = new ArrayList<String>();

			/*
			 * read every XYZ coordinate
			 * transform it to an vertex
			 * write an vertex for every needed layer
			 * result is a vertex grid for every layer
			 */
			System.out.println("write vetices...");
			while ((line = br.readLine()) != null) {
				String[] csv = line.split(" ");
				if (lauf == 0) { // soll - ist
					new_X = X1_SOLL - Double.parseDouble(csv[0]);
					new_Y = Y1_SOLL - Double.parseDouble(csv[1]);
				}
				double x = (Double.parseDouble(csv[0]) + new_X - BB_UPPER_LEFT_X - halfGridSize) / gridSize;
				double y = ((Double.parseDouble(csv[1]) + new_Y - BB_UPPER_LEFT_Y + halfGridSize) / gridSize) + y_offset;
				int z = -42;
				double r = -42.0;
				double g = -42.0;
				double b = -42.0;
				// ORIGINAL
				z = Integer.parseInt(csv[2]);
				String tmp = "v " + x + " " + y + " " + z;
				VERTEX_LIST_ORIG.add(tmp);
				// WATER
				z = z_water;
				r = rgb_water[0];
				g = rgb_water[1];
				b = rgb_water[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_WATER.add(tmp);
				// LANDSCAPE
				z = z_landscape;
				r = rgb_landscape[0];
				g = rgb_landscape[1];
				b = rgb_landscape[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_LANDSCAPE.add(tmp);
				// AREA 2 WATER
				z = z_area2_water;
				r = rgb_water[0];
				g = rgb_water[1];
				b = rgb_water[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA2_WATER.add(tmp);
				// AREA 2 LANDSCAPE
				z = z_area2_landscape;
				r = rgb_landscape[0];
				g = rgb_landscape[1];
				b = rgb_landscape[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA2_LANDSCAPE.add(tmp);
				// AREA 2
				z = z_area2;
				r = rgb_area[0];
				g = rgb_area[1];
				b = rgb_area[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA2.add(tmp);
				// AREA 3 WATER
				z = z_area3_water;
				r = rgb_water[0];
				g = rgb_water[1];
				b = rgb_water[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA3_WATER.add(tmp);
				// AREA 3 LANDSCAPE
				z = z_area3_landscape;
				r = rgb_landscape[0];
				g = rgb_landscape[1];
				b = rgb_landscape[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA3_LANDSCAPE.add(tmp);
				// AREA 3
				z = z_area3;
				r = rgb_area[0];
				g = rgb_area[1];
				b = rgb_area[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA3.add(tmp);
				// AREA 4 WATER
				z = z_area4_water;
				r = rgb_water[0];
				g = rgb_water[1];
				b = rgb_water[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA4_WATER.add(tmp);
				// AREA 4 LANDSCAPE
				z = z_area4_landscape;
				r = rgb_landscape[0];
				g = rgb_landscape[1];
				b = rgb_landscape[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA4_LANDSCAPE.add(tmp);
				// AREA 4
				z = z_area4;
				r = rgb_area[0];
				g = rgb_area[1];
				b = rgb_area[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA4.add(tmp);
				// AREA 5 WATER
				z = z_area5_water;
				r = rgb_water[0];
				g = rgb_water[1];
				b = rgb_water[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA5_WATER.add(tmp);
				// AREA 5 LANDSCAPE
				z = z_area5_landscape;
				r = rgb_landscape[0];
				g = rgb_landscape[1];
				b = rgb_landscape[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA5_LANDSCAPE.add(tmp);
				// AREA 5
				z = z_area5;
				r = rgb_area[0];
				g = rgb_area[1];
				b = rgb_area[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA5.add(tmp);
				// AREA 6 WATER
				z = z_area6_water;
				r = rgb_water[0];
				g = rgb_water[1];
				b = rgb_water[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA6_WATER.add(tmp);
				// AREA 6 LANDSCAPE
				z = z_area6_landscape;
				r = rgb_landscape[0];
				g = rgb_landscape[1];
				b = rgb_landscape[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA6_LANDSCAPE.add(tmp);
				// AREA 6
				z = z_area6;
				r = rgb_area[0];
				g = rgb_area[1];
				b = rgb_area[2];
				tmp = "v " + x + " " + y + " " + z + " " + r + " " + g + " " + b;
				VERTEX_LIST_AREA6.add(tmp);
				lauf++;
			}
			System.out.println("write vetices finished...");

			/*
			 * for every match point in a layer create two triangles
			 * upper left, lower right, lower left in the grid (center is the match point)
			 * upper left, upper right, lower right in the grid (center is the match point)
			 * create triangles (8) from the upper layer to the lower layer
			 * result is a extruded "pixel" from the lower layer to the upper layer
			 */
			System.out.println("write face sets Landscape...");
			// LANDSCAPE
			for (int i = 0; i < VERTEX_LIST_ORIG.size(); i++) {
				String[] vertex = VERTEX_LIST_ORIG.get(i).split(" ");
				if (Double.parseDouble(vertex[3]) > landscape_MIN && Double.parseDouble(vertex[3]) < landscape_MAX) {
					int m = (VERTEX_LIST_ORIG.size() * 1) + i;
					int m2 = (VERTEX_LIST_ORIG.size() * 0) + i;
					//
					int Z = (int) Math.floor(m / B);
					int a = (int) (m - (Z * B) - 1) + (Z - 1) * B;
					int b = (int) (m - (Z * B) - 1) + (Z + 1) * B;
					int c = (int) (a + 2);
					int d = (int) (b + 2);
					String tmp2 = "f " + (a) + " " + (d) + " " + (b);
					String tmp3 = "f " + (a) + " " + (c) + " " + (d);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					int Z2 = (int) Math.floor(m2 / B);
					int a2 = (int) (m2 - (Z2 * B) - 1) + (Z2 - 1) * B;
					int b2 = (int) (m2 - (Z2 * B) - 1) + (Z2 + 1) * B;
					int c2 = (int) (a2 + 2);
					int d2 = (int) (b2 + 2);
					tmp2 = "f " + (a) + " " + (b2) + " " + (a2);
					tmp3 = "f " + (a) + " " + (b) + " " + (b2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (b) + " " + (d2) + " " + (b2);
					tmp3 = "f " + (b) + " " + (d) + " " + (d2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (d) + " " + (c2) + " " + (d2);
					tmp3 = "f " + (d) + " " + (c) + " " + (c2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (c) + " " + (a2) + " " + (c2);
					tmp3 = "f " + (c) + " " + (a) + " " + (a2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
				}
			}
			System.out.println("write face sets (landscape) Area 2...");
			// AREA 2 LANDSCAPE
			for (int i = 0; i < VERTEX_LIST_ORIG.size(); i++) {
				String[] vertex = VERTEX_LIST_ORIG.get(i).split(" ");
				if (Double.parseDouble(vertex[3]) > landscape_MIN && Double.parseDouble(vertex[3]) < landscape_MAX) {
					int m = (VERTEX_LIST_ORIG.size() * 3) + i;
					int m2 = (VERTEX_LIST_ORIG.size() * 2) + i;
					//
					int Z = (int) Math.floor(m / B);
					int a = (int) (m - (Z * B) - 1) + (Z - 1) * B;
					int b = (int) (m - (Z * B) - 1) + (Z + 1) * B;
					int c = (int) (a + 2);
					int d = (int) (b + 2);
					String tmp2 = "f " + (a) + " " + (d) + " " + (b);
					String tmp3 = "f " + (a) + " " + (c) + " " + (d);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					int Z2 = (int) Math.floor(m2 / B);
					int a2 = (int) (m2 - (Z2 * B) - 1) + (Z2 - 1) * B;
					int b2 = (int) (m2 - (Z2 * B) - 1) + (Z2 + 1) * B;
					int c2 = (int) (a2 + 2);
					int d2 = (int) (b2 + 2);
					tmp2 = "f " + (a) + " " + (b2) + " " + (a2);
					tmp3 = "f " + (a) + " " + (b) + " " + (b2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (b) + " " + (d2) + " " + (b2);
					tmp3 = "f " + (b) + " " + (d) + " " + (d2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (d) + " " + (c2) + " " + (d2);
					tmp3 = "f " + (d) + " " + (c) + " " + (c2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (c) + " " + (a2) + " " + (c2);
					tmp3 = "f " + (c) + " " + (a) + " " + (a2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
				}
			}
			System.out.println("write face sets (area) Area 2...");
			// AREA 2
			for (int i = 0; i < VERTEX_LIST_ORIG.size(); i++) {
				String[] vertex = VERTEX_LIST_ORIG.get(i).split(" ");
				//if (Double.parseDouble(vertex[3]) == 2) {
				if (Addition.match(Integer.parseInt(vertex[3]), values[0])) {
					int m = (VERTEX_LIST_ORIG.size() * 4) + i;
					int m2 = (VERTEX_LIST_ORIG.size() * 3) + i;
					//
					int Z = (int) Math.floor(m / B);
					int a = (int) (m - (Z * B) - 1) + (Z - 1) * B;
					int b = (int) (m - (Z * B) - 1) + (Z + 1) * B;
					int c = (int) (a + 2);
					int d = (int) (b + 2);
					String tmp2 = "f " + (a) + " " + (d) + " " + (b);
					String tmp3 = "f " + (a) + " " + (c) + " " + (d);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					int Z2 = (int) Math.floor(m2 / B);
					int a2 = (int) (m2 - (Z2 * B) - 1) + (Z2 - 1) * B;
					int b2 = (int) (m2 - (Z2 * B) - 1) + (Z2 + 1) * B;
					int c2 = (int) (a2 + 2);
					int d2 = (int) (b2 + 2);
					tmp2 = "f " + (a) + " " + (b2) + " " + (a2);
					tmp3 = "f " + (a) + " " + (b) + " " + (b2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (b) + " " + (d2) + " " + (b2);
					tmp3 = "f " + (b) + " " + (d) + " " + (d2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (d) + " " + (c2) + " " + (d2);
					tmp3 = "f " + (d) + " " + (c) + " " + (c2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (c) + " " + (a2) + " " + (c2);
					tmp3 = "f " + (c) + " " + (a) + " " + (a2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
				}
			}
			System.out.println("write face sets (landscape) Area 3...");
			// AREA 3 LANDSCAPE
			for (int i = 0; i < VERTEX_LIST_ORIG.size(); i++) {
				String[] vertex = VERTEX_LIST_ORIG.get(i).split(" ");
				if (Double.parseDouble(vertex[3]) > landscape_MIN && Double.parseDouble(vertex[3]) < landscape_MAX) {
					int m = (VERTEX_LIST_ORIG.size() * 6) + i;
					int m2 = (VERTEX_LIST_ORIG.size() * 5) + i;
					//
					int Z = (int) Math.floor(m / B);
					int a = (int) (m - (Z * B) - 1) + (Z - 1) * B;
					int b = (int) (m - (Z * B) - 1) + (Z + 1) * B;
					int c = (int) (a + 2);
					int d = (int) (b + 2);
					String tmp2 = "f " + (a) + " " + (d) + " " + (b);
					String tmp3 = "f " + (a) + " " + (c) + " " + (d);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					int Z2 = (int) Math.floor(m2 / B);
					int a2 = (int) (m2 - (Z2 * B) - 1) + (Z2 - 1) * B;
					int b2 = (int) (m2 - (Z2 * B) - 1) + (Z2 + 1) * B;
					int c2 = (int) (a2 + 2);
					int d2 = (int) (b2 + 2);
					tmp2 = "f " + (a) + " " + (b2) + " " + (a2);
					tmp3 = "f " + (a) + " " + (b) + " " + (b2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (b) + " " + (d2) + " " + (b2);
					tmp3 = "f " + (b) + " " + (d) + " " + (d2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (d) + " " + (c2) + " " + (d2);
					tmp3 = "f " + (d) + " " + (c) + " " + (c2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (c) + " " + (a2) + " " + (c2);
					tmp3 = "f " + (c) + " " + (a) + " " + (a2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
				}
			}
			System.out.println("write face sets (area) Area 3...");
			// AREA 3
			for (int i = 0; i < VERTEX_LIST_ORIG.size(); i++) {
				String[] vertex = VERTEX_LIST_ORIG.get(i).split(" ");
				//if (Double.parseDouble(vertex[3]) == 3) {
				if (Addition.match(Integer.parseInt(vertex[3]), values[1])) {
					int m = (VERTEX_LIST_ORIG.size() * 7) + i;
					int m2 = (VERTEX_LIST_ORIG.size() * 6) + i;
					//
					int Z = (int) Math.floor(m / B);
					int a = (int) (m - (Z * B) - 1) + (Z - 1) * B;
					int b = (int) (m - (Z * B) - 1) + (Z + 1) * B;
					int c = (int) (a + 2);
					int d = (int) (b + 2);
					String tmp2 = "f " + (a) + " " + (d) + " " + (b);
					String tmp3 = "f " + (a) + " " + (c) + " " + (d);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					int Z2 = (int) Math.floor(m2 / B);
					int a2 = (int) (m2 - (Z2 * B) - 1) + (Z2 - 1) * B;
					int b2 = (int) (m2 - (Z2 * B) - 1) + (Z2 + 1) * B;
					int c2 = (int) (a2 + 2);
					int d2 = (int) (b2 + 2);
					tmp2 = "f " + (a) + " " + (b2) + " " + (a2);
					tmp3 = "f " + (a) + " " + (b) + " " + (b2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (b) + " " + (d2) + " " + (b2);
					tmp3 = "f " + (b) + " " + (d) + " " + (d2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (d) + " " + (c2) + " " + (d2);
					tmp3 = "f " + (d) + " " + (c) + " " + (c2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (c) + " " + (a2) + " " + (c2);
					tmp3 = "f " + (c) + " " + (a) + " " + (a2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
				}
			}
			System.out.println("write face sets (landscape) Area 4...");
			// AREA 4 LANDSCAPE
			for (int i = 0; i < VERTEX_LIST_ORIG.size(); i++) {
				String[] vertex = VERTEX_LIST_ORIG.get(i).split(" ");
				if (Double.parseDouble(vertex[3]) > landscape_MIN && Double.parseDouble(vertex[3]) < landscape_MAX) {
					int m = (VERTEX_LIST_ORIG.size() * 9) + i;
					int m2 = (VERTEX_LIST_ORIG.size() * 8) + i;
					//
					int Z = (int) Math.floor(m / B);
					int a = (int) (m - (Z * B) - 1) + (Z - 1) * B;
					int b = (int) (m - (Z * B) - 1) + (Z + 1) * B;
					int c = (int) (a + 2);
					int d = (int) (b + 2);
					String tmp2 = "f " + (a) + " " + (d) + " " + (b);
					String tmp3 = "f " + (a) + " " + (c) + " " + (d);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					int Z2 = (int) Math.floor(m2 / B);
					int a2 = (int) (m2 - (Z2 * B) - 1) + (Z2 - 1) * B;
					int b2 = (int) (m2 - (Z2 * B) - 1) + (Z2 + 1) * B;
					int c2 = (int) (a2 + 2);
					int d2 = (int) (b2 + 2);
					tmp2 = "f " + (a) + " " + (b2) + " " + (a2);
					tmp3 = "f " + (a) + " " + (b) + " " + (b2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (b) + " " + (d2) + " " + (b2);
					tmp3 = "f " + (b) + " " + (d) + " " + (d2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (d) + " " + (c2) + " " + (d2);
					tmp3 = "f " + (d) + " " + (c) + " " + (c2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (c) + " " + (a2) + " " + (c2);
					tmp3 = "f " + (c) + " " + (a) + " " + (a2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
				}
			}
			System.out.println("write face sets (area) Area 4...");
			// AREA 4
			for (int i = 0; i < VERTEX_LIST_ORIG.size(); i++) {
				String[] vertex = VERTEX_LIST_ORIG.get(i).split(" ");
				//if (Double.parseDouble(vertex[3]) == 4) {
				if (Addition.match(Integer.parseInt(vertex[3]), values[2])) {
					int m = (VERTEX_LIST_ORIG.size() * 10) + i;
					int m2 = (VERTEX_LIST_ORIG.size() * 9) + i;
					//
					int Z = (int) Math.floor(m / B);
					int a = (int) (m - (Z * B) - 1) + (Z - 1) * B;
					int b = (int) (m - (Z * B) - 1) + (Z + 1) * B;
					int c = (int) (a + 2);
					int d = (int) (b + 2);
					String tmp2 = "f " + (a) + " " + (d) + " " + (b);
					String tmp3 = "f " + (a) + " " + (c) + " " + (d);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					int Z2 = (int) Math.floor(m2 / B);
					int a2 = (int) (m2 - (Z2 * B) - 1) + (Z2 - 1) * B;
					int b2 = (int) (m2 - (Z2 * B) - 1) + (Z2 + 1) * B;
					int c2 = (int) (a2 + 2);
					int d2 = (int) (b2 + 2);
					tmp2 = "f " + (a) + " " + (b2) + " " + (a2);
					tmp3 = "f " + (a) + " " + (b) + " " + (b2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (b) + " " + (d2) + " " + (b2);
					tmp3 = "f " + (b) + " " + (d) + " " + (d2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (d) + " " + (c2) + " " + (d2);
					tmp3 = "f " + (d) + " " + (c) + " " + (c2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (c) + " " + (a2) + " " + (c2);
					tmp3 = "f " + (c) + " " + (a) + " " + (a2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
				}
			}
			System.out.println("write face sets (landscape) Area 5...");
			// AREA 5 LANDSCAPE
			for (int i = 0; i < VERTEX_LIST_ORIG.size(); i++) {
				String[] vertex = VERTEX_LIST_ORIG.get(i).split(" ");
				if (Double.parseDouble(vertex[3]) > landscape_MIN && Double.parseDouble(vertex[3]) < landscape_MAX) {
					int m = (VERTEX_LIST_ORIG.size() * 12) + i;
					int m2 = (VERTEX_LIST_ORIG.size() * 11) + i;
					//
					int Z = (int) Math.floor(m / B);
					int a = (int) (m - (Z * B) - 1) + (Z - 1) * B;
					int b = (int) (m - (Z * B) - 1) + (Z + 1) * B;
					int c = (int) (a + 2);
					int d = (int) (b + 2);
					String tmp2 = "f " + (a) + " " + (d) + " " + (b);
					String tmp3 = "f " + (a) + " " + (c) + " " + (d);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					int Z2 = (int) Math.floor(m2 / B);
					int a2 = (int) (m2 - (Z2 * B) - 1) + (Z2 - 1) * B;
					int b2 = (int) (m2 - (Z2 * B) - 1) + (Z2 + 1) * B;
					int c2 = (int) (a2 + 2);
					int d2 = (int) (b2 + 2);
					tmp2 = "f " + (a) + " " + (b2) + " " + (a2);
					tmp3 = "f " + (a) + " " + (b) + " " + (b2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (b) + " " + (d2) + " " + (b2);
					tmp3 = "f " + (b) + " " + (d) + " " + (d2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (d) + " " + (c2) + " " + (d2);
					tmp3 = "f " + (d) + " " + (c) + " " + (c2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (c) + " " + (a2) + " " + (c2);
					tmp3 = "f " + (c) + " " + (a) + " " + (a2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
				}
			}
			System.out.println("write face sets (area) Area 5...");
			// AREA 5
			for (int i = 0; i < VERTEX_LIST_ORIG.size(); i++) {
				String[] vertex = VERTEX_LIST_ORIG.get(i).split(" ");
				//if (Double.parseDouble(vertex[3]) == 5) {
				if (Addition.match(Integer.parseInt(vertex[3]), values[3])) {
					int m = (VERTEX_LIST_ORIG.size() * 13) + i;
					int m2 = (VERTEX_LIST_ORIG.size() * 12) + i;
					//
					int Z = (int) Math.floor(m / B);
					int a = (int) (m - (Z * B) - 1) + (Z - 1) * B;
					int b = (int) (m - (Z * B) - 1) + (Z + 1) * B;
					int c = (int) (a + 2);
					int d = (int) (b + 2);
					String tmp2 = "f " + (a) + " " + (d) + " " + (b);
					String tmp3 = "f " + (a) + " " + (c) + " " + (d);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					int Z2 = (int) Math.floor(m2 / B);
					int a2 = (int) (m2 - (Z2 * B) - 1) + (Z2 - 1) * B;
					int b2 = (int) (m2 - (Z2 * B) - 1) + (Z2 + 1) * B;
					int c2 = (int) (a2 + 2);
					int d2 = (int) (b2 + 2);
					tmp2 = "f " + (a) + " " + (b2) + " " + (a2);
					tmp3 = "f " + (a) + " " + (b) + " " + (b2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (b) + " " + (d2) + " " + (b2);
					tmp3 = "f " + (b) + " " + (d) + " " + (d2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (d) + " " + (c2) + " " + (d2);
					tmp3 = "f " + (d) + " " + (c) + " " + (c2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (c) + " " + (a2) + " " + (c2);
					tmp3 = "f " + (c) + " " + (a) + " " + (a2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
				}
			}
			System.out.println("write face sets (landscape) Area 6...");
			// AREA 6 LANDSCAPE
			for (int i = 0; i < VERTEX_LIST_ORIG.size(); i++) {
				String[] vertex = VERTEX_LIST_ORIG.get(i).split(" ");
				if (Double.parseDouble(vertex[3]) > landscape_MIN && Double.parseDouble(vertex[3]) < landscape_MAX) {
					int m = (VERTEX_LIST_ORIG.size() * 15) + i;
					int m2 = (VERTEX_LIST_ORIG.size() * 14) + i;
					//
					int Z = (int) Math.floor(m / B);
					int a = (int) (m - (Z * B) - 1) + (Z - 1) * B;
					int b = (int) (m - (Z * B) - 1) + (Z + 1) * B;
					int c = (int) (a + 2);
					int d = (int) (b + 2);
					String tmp2 = "f " + (a) + " " + (d) + " " + (b);
					String tmp3 = "f " + (a) + " " + (c) + " " + (d);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					int Z2 = (int) Math.floor(m2 / B);
					int a2 = (int) (m2 - (Z2 * B) - 1) + (Z2 - 1) * B;
					int b2 = (int) (m2 - (Z2 * B) - 1) + (Z2 + 1) * B;
					int c2 = (int) (a2 + 2);
					int d2 = (int) (b2 + 2);
					tmp2 = "f " + (a) + " " + (b2) + " " + (a2);
					tmp3 = "f " + (a) + " " + (b) + " " + (b2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (b) + " " + (d2) + " " + (b2);
					tmp3 = "f " + (b) + " " + (d) + " " + (d2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (d) + " " + (c2) + " " + (d2);
					tmp3 = "f " + (d) + " " + (c) + " " + (c2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (c) + " " + (a2) + " " + (c2);
					tmp3 = "f " + (c) + " " + (a) + " " + (a2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
				}
			}
			System.out.println("write face sets (area) Area 6...");
			// AREA 5
			for (int i = 0; i < VERTEX_LIST_ORIG.size(); i++) {
				String[] vertex = VERTEX_LIST_ORIG.get(i).split(" ");
				//if (Double.parseDouble(vertex[3]) == 6) {
				if (Addition.match(Integer.parseInt(vertex[3]), values[4])) {
					int m = (VERTEX_LIST_ORIG.size() * 16) + i;
					int m2 = (VERTEX_LIST_ORIG.size() * 15) + i;
					//
					int Z = (int) Math.floor(m / B);
					int a = (int) (m - (Z * B) - 1) + (Z - 1) * B;
					int b = (int) (m - (Z * B) - 1) + (Z + 1) * B;
					int c = (int) (a + 2);
					int d = (int) (b + 2);
					String tmp2 = "f " + (a) + " " + (d) + " " + (b);
					String tmp3 = "f " + (a) + " " + (c) + " " + (d);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					int Z2 = (int) Math.floor(m2 / B);
					int a2 = (int) (m2 - (Z2 * B) - 1) + (Z2 - 1) * B;
					int b2 = (int) (m2 - (Z2 * B) - 1) + (Z2 + 1) * B;
					int c2 = (int) (a2 + 2);
					int d2 = (int) (b2 + 2);
					tmp2 = "f " + (a) + " " + (b2) + " " + (a2);
					tmp3 = "f " + (a) + " " + (b) + " " + (b2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (b) + " " + (d2) + " " + (b2);
					tmp3 = "f " + (b) + " " + (d) + " " + (d2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (d) + " " + (c2) + " " + (d2);
					tmp3 = "f " + (d) + " " + (c) + " " + (c2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
					tmp2 = "f " + (c) + " " + (a2) + " " + (c2);
					tmp3 = "f " + (c) + " " + (a) + " " + (a2);
					FACESET_LIST.add(tmp2);
					FACESET_LIST.add(tmp3);
				}
			}
			////////////
			// OUTPUT //
			////////////
			System.out.println("write file...");
			File file = new File(output_path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			try (BufferedWriter bw = new BufferedWriter(fw)) {
				int vertex_count = VERTEX_LIST_WATER.size() + VERTEX_LIST_LANDSCAPE.size() + VERTEX_LIST_AREA2_WATER.size() + VERTEX_LIST_AREA2_LANDSCAPE.size() + VERTEX_LIST_AREA2.size() + VERTEX_LIST_AREA4_WATER.size() + VERTEX_LIST_AREA4_LANDSCAPE.size() + VERTEX_LIST_AREA4.size() + VERTEX_LIST_AREA5_WATER.size() + VERTEX_LIST_AREA5_LANDSCAPE.size() + VERTEX_LIST_AREA5.size() + VERTEX_LIST_AREA6_WATER.size() + VERTEX_LIST_AREA6_LANDSCAPE.size() + VERTEX_LIST_AREA6.size() + VERTEX_LIST_AREA2_WATER.size() + VERTEX_LIST_AREA2_LANDSCAPE.size() + VERTEX_LIST_AREA2.size();
				DecimalFormat nf = new DecimalFormat();
				bw.write("# Vertices: " + nf.format(vertex_count) + "\r\n");
				bw.write("# Faces: : " + nf.format(FACESET_LIST.size()) + "\r\n");
				System.out.println("Vertices: " + nf.format(vertex_count));
				System.out.println("Faces: " + nf.format(FACESET_LIST.size()));
				for (String VERTEX_LIST1 : VERTEX_LIST_WATER) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_LANDSCAPE) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA2_WATER) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA2_LANDSCAPE) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA2) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA3_WATER) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA3_LANDSCAPE) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA3) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA4_WATER) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA4_LANDSCAPE) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA4) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA5_WATER) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA5_LANDSCAPE) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA5) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA6_WATER) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA6_LANDSCAPE) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String VERTEX_LIST1 : VERTEX_LIST_AREA6) {
					bw.write(VERTEX_LIST1 + "\r\n");
				}
				for (String FACESET_LIST1 : FACESET_LIST) {
					bw.write(FACESET_LIST1 + "\r\n");
				}
				System.out.println("FINISH!");
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error", e);
		}
	}
}