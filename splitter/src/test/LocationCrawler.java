/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 *
 * @author PowerWBH
 */
public class LocationCrawler {

	private static String ProcessLocationRequest(String QueryItem) {
		try {
			QueryItem = URLEncoder.encode(QueryItem, "ISO-8859-1");
			String URLStr = String.format("https://maps.googleapis.com/maps/api/geocode/json?"
					+ "address=%s&sensor=false&key=%s",
					QueryItem, 
					//"AIzaSyAOkEu7MBxUj4t2pBq1GZ-0Td7cf7bOKTg");
					//"AIzaSyCvzTx408371P7CtoXN8BejAAmBa0NUZbc");
					"AIzaSyAIyQ3XaHrC9TQVMIs65IrwzREtnzqZRKw");
			URL url = new URL(URLStr);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			if (connection.getResponseCode() == 200) {
				InputStream is = connection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				String sb = "";
				String str;

				while ((str = br.readLine()) != null) {
					sb += (str);
				}

				br.close();

				return sb;
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}

	public static String getLocationCoord(String Address) {
		String jsonString = ProcessLocationRequest(Address);
		String LocationCoord = "";

		if (jsonString.length() > 0) {
			try {
				ObjectMapper m = new ObjectMapper();
				JsonNode rootNode = m.readTree(jsonString);
				for (JsonNode result : rootNode.path("results")) {
					JsonNode LatLongNode = result.path("geometry").path("location");
					LocationCoord = String.format("%s|%s",
							LatLongNode.path("lat").doubleValue(), LatLongNode.path("lng").doubleValue());
					break;
				}

			} catch (JsonProcessingException jpe) {
				System.out.println(jpe.getMessage());
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				return LocationCoord;
			}
		} else {
			return LocationCoord;
		}
	}
}
