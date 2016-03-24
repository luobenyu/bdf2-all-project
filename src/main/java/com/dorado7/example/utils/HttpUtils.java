/**
 * HttpUtils.java
 * com.dorado7.example.utils
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-12-24 		小山
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.dorado7.example.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.htmlcleaner.BrowserCompactXmlSerializer;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;

import com.dorado7.example.annotation.DetailName;
import com.dorado7.example.entity.LostCredit;
import com.dorado7.example.entity.QueryResult;

/**
 * ClassName:HttpUtils
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   小山
 * @version  
 * @since    Ver 1.1
 * @Date	 2015-12-24		上午9:58:53
 *
 * @see 	 
 */
public class HttpUtils {
	
	/**
	 * 
	 * search:
	 *
	 * @return List<QueryResult> 
	 * @throws Exception 
	 * @throws 
	 * @since 　Ver 1.1
	 */
	@SuppressWarnings("unchecked")
	public static List<QueryResult> search(String str) throws Exception{
		List<QueryResult> list = searchAll(str);
		for(QueryResult result : list){
			Map<String, Object> map = detailsByXPath(result.getEncryStr());
			List<LostCredit> details = (List<LostCredit>) map.get("list");
			result.setStr((String)map.get("str"));
			result.setDetails(details);
		}
		return list;
	}
	
	/**
	 * 
	 * detailsByXPath:
	 *
	 * @return void 
	 * @throws 
	 * @since 　Ver 1.1
	 */
	private static Map<String,Object> detailsByXPath(String encryStr) throws Exception{
		Map<String, Object> mapA = new HashMap<String, Object>();
		String htmlStr = doGet("http://www.creditchina.gov.cn/channel_record_detail/"
									+encryStr.replace("\n", ""),100000,"utf-8");
		String xmlStr = html2Xml(htmlStr);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
			    .newDocumentBuilder();
		Document document = builder.parse(new ByteArrayInputStream(xmlStr.getBytes()));
		XPath xpath = XPathFactory.newInstance().newXPath();
		org.w3c.dom.NodeList nodeList 
							= (org.w3c.dom.NodeList) xpath.evaluate(
							"//header[h1[contains(text(),'失信记录')]]/following-sibling::div/ul", document,
							XPathConstants.NODESET);
		String detailsStr =  "";
		List<LostCredit> list = new ArrayList<LostCredit>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			detailsStr +="<ul>";
            String content = nodeList.item(i).getTextContent().replace(" ", "");
            nodeList.item(i).getOwnerDocument();
            String[] temp = content.split("\n");
            List<String> listArr = Arrays.asList(temp);
            Map<String,String> map = new HashMap<String, String>();
            for(String str : listArr){
            	if(str.length()>2){
            		temp = str.split("：");
            		for(int j = 1;j<temp.length;j++){
            			str = "";
            			str += temp[j];
            		}
            		map.put(temp[0], str);
            	}
            }
            LostCredit lostCredit = new LostCredit();
            Class<?> clazz = lostCredit.getClass();
            Method[] methods = clazz.getDeclaredMethods();
            for(Method method : methods){
            	if(method.getName().startsWith("set")){
	            	DetailName name = method.getAnnotation(DetailName.class);
	            	String mapStr = map.get(name.value());
	            	detailsStr += "<li><Strong>"+name.value()+"：</Strong>"+mapStr+"</li>";
	            	method.invoke(lostCredit, mapStr);
            	}
            }
            detailsStr += "</ul><br>";
            list.add(lostCredit);
        }
		mapA.put("str", detailsStr);
		mapA.put("list", list);
		return mapA;
		
	}
	
	/**
	 * 
	 * html2Xml:
	 *
	 * @return String 
	 * @throws 
	 * @since 　Ver 1.1
	 */
	public static String html2Xml(String htmlStr) throws Exception{
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode node = htmlCleaner.clean(htmlStr);
		Object[] nodes = node.getElementsByName("body", true);
		TagNode body = (TagNode) nodes[0];
		CleanerProperties properties = htmlCleaner.getProperties();
		BrowserCompactXmlSerializer xmlSerializer = new BrowserCompactXmlSerializer(properties);
		String xmlStr = xmlSerializer.getAsString(body);
		return xmlStr;
	}
	
	@SuppressWarnings("unchecked")
	private static List<QueryResult> searchAll(String str){
		Integer currPage = 1;
		Integer totalPage = 1;
		List<QueryResult> list = new ArrayList<QueryResult>();
		while(currPage<=totalPage){
			Map<String,String> map = new HashMap<String, String>();
			map.put("keyword", str);
			map.put("page", currPage.toString());
			String result = doPost("http://www.creditchina.gov.cn/channel_record",100000,"UTF-8",map);
			try{
				list.addAll((List<QueryResult>)convertJson(result).get("result"));
				totalPage = Integer.parseInt(convertJson(result).get("totalPage").toString());
				currPage++;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return list;
	}
	
	private static Map<String,Object> convertJson(String jsonStr) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(jsonStr);
		JsonNode results = rootNode.path("result").findPath("results");
		Integer currPage = rootNode.path("result").findPath("currentPageNo").asInt();
		Integer totalPage = rootNode.path("result").findPath("totalPageCount").asInt();
		List<QueryResult> list = new ArrayList<QueryResult>();
		for(JsonNode node : results){
			QueryResult result = new QueryResult();
			result.setName(node.path("name").asText());
			result.setEncryStr(node.path("encryStr").asText());
			result.setGoodCount(node.path("gooCount").asText());
			result.setBadCount(node.path("badCount").asText());
			result.setDishonestyCount(node.path("dishonestyCount").asText());
			result.setId(node.path("id").asText());
			result.setObjectType(node.path("objectType").asText());
			result.setType(node.path("type").asText());
			result.setSource(node.path("source").asText());
			result.setTime(node.path("time").asText());
			result.setContent(node.path("content").toString());
			list.add(result);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result",list);
		map.put("currPage",currPage);
		map.put("totalPage",totalPage);
		return map;
	}
	
	/**
	 * doGet:
	 *
	 * @param  @param url
	 * @param  @param timeout
	 * @param  @param charset
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	private static String doGet(String url, int timeout, String charset){
		if (charset == null)
	          charset = "utf-8";
	    HttpMethod method = new GetMethod(url);
		return doHttp(method,timeout,charset);
	}
	
	/**
	 * 
	 * doPost: post请求
	 *
	 * @param  @param url
	 * @param  @param timeout
	 * @param  @param charset
	 * @param  @param params
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	private static String doPost(String url, int timeout, String charset,Map<String,String> params){
        if (charset == null)
          charset = "utf-8";
        PostMethod method = new PostMethod(url);
        method.addRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        
        NameValuePair[] nameValues =new NameValuePair[params.size()];
        NameValuePair simcard;
        String[] keys=params.keySet().toArray(new String[params.size()]);
        String key;
        for(int i=0;i<keys.length;i++){
              key=keys[i];
              simcard = new NameValuePair(key,params.get(key));
              nameValues[i]=simcard;
        }
        method.setRequestBody(nameValues);
        return doHttp(method,timeout,charset);
     }
	
	/**
	 * 
	 * doHttp:发送请求
	 * @param  	result
	 * @param   timeout
	 * @param   charset
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	private static String doHttp(HttpMethod result,int timeout, String charset){
		 HttpClient client = new HttpClient();
         try {
               HttpConnectionManagerParams managerParams = client
                 .getHttpConnectionManager().getParams();

               managerParams.setConnectionTimeout(timeout);
               client.executeMethod(result);          
               InputStream resStream = result.getResponseBodyAsStream();
               BufferedReader br = new BufferedReader(new InputStreamReader(resStream, charset));
               StringBuffer resBuffer = new StringBuffer();
               String resTemp = "";
               while ((resTemp = br.readLine()) != null) {
                 resBuffer.append(resTemp);
               }
               return resBuffer.toString();
             }
             catch (HttpException e)
             {
               return null;
             }
             catch (IOException e) {
               return null;
             }
             catch (Exception e) {
               return null;
             } finally {
               result.releaseConnection();
             }
	}
	
}

