package com.streep.openglleer.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.streep.openglleer.core.Mesh;

public class FileUtils {

	public static String loadFile(String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Mesh importOBJ(String filepath, Texture texture) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			String line;
			List<Vector3f> verticesList = new ArrayList<Vector3f>();
			List<Vector2f> uvList = new ArrayList<Vector2f>();
			List<Vector3f> normalList = new ArrayList<Vector3f>();
			List<Integer> indicesList = new ArrayList<Integer>();
			float[] vertices = new float[0];
			float[] normals = new float[0];
			float[] uv = new float[0];
			int[] indices = new int[0];
			while(true) {
				line = br.readLine();
				String[] currentLine = line.split(" ");
				if(line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]),Float.parseFloat(currentLine[3]));
					verticesList.add(vertex);
				} else if(line.startsWith("vt ")) {
					Vector2f uvVec = new Vector2f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]));
					uvList.add(uvVec);
				} else if(line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]),Float.parseFloat(currentLine[3]));
					normalList.add(normal);
				} else if(line.startsWith("f ")) {
					uv = new float[verticesList.size()*2];
					normals = new float[verticesList.size()*3];
					break;
				}
			}
			
			while(line != null) {
				if(!line.startsWith("f ")) {
					line = br.readLine();
					continue;
				}
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				processVertex(vertex1, indicesList, uvList, normalList, uv, normals);
				processVertex(vertex2, indicesList, uvList, normalList, uv, normals);
				processVertex(vertex3, indicesList, uvList, normalList, uv, normals);
				line = br.readLine();
			}
			br.close();
			vertices = new float[verticesList.size() * 3];
			indices = new int[indicesList.size()];
			int vertexPointer = 0;
			for(Vector3f vec : verticesList) {
				vertices[vertexPointer++] = vec.x;
				vertices[vertexPointer++] = vec.y;
				vertices[vertexPointer++] = vec.z;
			}
			for(int i = 0; i < indicesList.size(); i++) {
				indices[i] = indicesList.get(i);
			}
			Mesh m = new Mesh(vertices,indices,uv,texture); //uv, normals
			return m;
		} catch(Exception e) {
			System.err.println("Could not load obj file!");
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}
	
	private static void processVertex(String[] data, List<Integer> indicesList, List<Vector2f> uvList, List<Vector3f> normalsList, float[] uv, float[] normals) {
		int currentVertexPointer = Integer.parseInt(data[0]) - 1;
		indicesList.add(currentVertexPointer);
		Vector2f currentUV = new Vector2f(0,0);
		if(data[1].length() > 1) {
			currentUV = uvList.get(Integer.parseInt(data[1]) - 1);
		}
		uv[currentVertexPointer*2] = currentUV.x;
		uv[currentVertexPointer*2+1] = 1 - currentUV.y;
		Vector3f currentNormal = normalsList.get(Integer.parseInt(data[2]) - 1);
		normals[currentVertexPointer*3] = currentNormal.x;
		normals[currentVertexPointer*3+1] = currentNormal.y;
		normals[currentVertexPointer*3+2] = currentNormal.z;
		
	}
}
