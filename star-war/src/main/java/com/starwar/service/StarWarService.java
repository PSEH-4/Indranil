package com.starwar.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import com.starwar.dto.ReturningDTO;
import com.starwar.exceptions.RestResponseEntityExceptionHandler;

@Service
public class StarWarService {
	@Autowired
	RestResponseEntityExceptionHandler exceptionHandler;


	RestTemplate rt = new RestTemplate();

	public ReturningDTO getAllTypes(String type, String qName) {

		String uri = "https://swapi.co/api/" + type.toLowerCase();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> response = rt.exchange(uri, HttpMethod.GET, entity, String.class);

		String Sjson = response.getBody();

		JSONObject object = new JSONObject(Sjson);

		JSONArray getArray = object.getJSONArray("results");

		Map<String, List<String>> map = new HashMap<>();

		for (int i = 0; i < getArray.length(); i++) {
			JSONObject objects = getArray.getJSONObject(i);
			JSONArray flims = objects.getJSONArray("films");
			String name = objects.getString("name");

			map.put(name, cleanUp(flims.toString()));

		}
		ReturningDTO rdto = new ReturningDTO();
		rdto.setName(qName);
		rdto.setType(type);
		if (map.containsKey(qName)) {
			rdto.setCount(map.get(qName).size());
			rdto.setFilms(map.get(qName));
		} else {
			rdto.setCount(0);
			rdto.setFilms(new ArrayList<String>());
		}

		return rdto;

	}

	public static List<String> cleanUp(String stri) {

		stri = stri.substring(2, stri.length() - 1);

		String[] arr = stri.split(",");
		List<String> list = Arrays.stream(arr).map(s -> s.replace("\"", "")).collect(Collectors.toList());

		return list;

	}

}
