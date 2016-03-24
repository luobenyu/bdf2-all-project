package com.cupdata.xxs.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
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
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.HasSiblingFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.w3c.dom.Document;

public class HttpClientTest {
	
	public static void main(String[] args) throws Exception {
		HttpClientTest test = new HttpClientTest();
		//模拟搜索动作
		List<Result> list = test.searchAll("李刚");
		
		//模拟点击动作
		for(Result result : list){
			System.out.println(result.getName());
			List<Detail> details = test.detailsByXPath(result.getEncryStr());
			result.setDetails(details);
		}
		System.out.println(list.size());
		
	}
	
	/**
	 * 
	 * detailsByXPath:
	 *
	 * @return void 
	 * @throws 
	 * @since 　Ver 1.1
	 */
	public List<Detail> detailsByXPath(String encryStr) throws Exception{
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
		List<Detail> list = new ArrayList<Detail>();
		for (int i = 0; i < nodeList.getLength(); i++) {
            String content = nodeList.item(i).getTextContent().replace(" ", "");
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
            Detail detail = new Detail();
            Class<?> clazz = detail.getClass();
            Method[] methods = clazz.getDeclaredMethods();
            for(Method method : methods){
            	if(method.getName().startsWith("set")){
	            	DetailName name = method.getAnnotation(DetailName.class);
	            	String mapStr = map.get(name.value());
	            	method.invoke(detail, mapStr);
            	}
            }
            list.add(detail);
        }
		return list;
		
	}
	
	/**
	 * 
	 * html2Xml:
	 *
	 * @return String 
	 * @throws 
	 * @since 　Ver 1.1
	 */
	public String html2Xml(String htmlStr) throws Exception{
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode node = htmlCleaner.clean(htmlStr);
		Object[] nodes = node.getElementsByName("body", true);
		TagNode body = (TagNode) nodes[0];
		CleanerProperties properties = htmlCleaner.getProperties();
		BrowserCompactXmlSerializer xmlSerializer = new BrowserCompactXmlSerializer(properties);
		String xmlStr = xmlSerializer.getAsString(body);
		return xmlStr;
	}
	
	/**
	 * 
	 * detailsByHtmlParser:
	 *
	 * @return void 
	 * @throws 
	 * @since 　Ver 1.1
	 * @deprecated
	 */
	public void detailsByHtmlParser(String encryStr) throws Exception{
		Parser parser = 
				new Parser( (HttpURLConnection) (new java.net.URL(
						"http://www.creditchina.gov.cn/channel_record_detail/"+encryStr.replace("\n", "")))
						.openConnection());
		TagNameFilter tagNameFilter = new TagNameFilter("header");
		HasSiblingFilter siblingFilter = new HasSiblingFilter(tagNameFilter);
		NodeFilter filter = new HasParentFilter(siblingFilter);
		
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		
		
		
		if(nodes!=null) {  
            for (int i = 0; i < nodes.size(); i++) {  
                Node textnode = (Node) nodes.elementAt(i);  
                  
                System.out.println("getText:"+textnode.toPlainTextString());
                System.out.println("=================================================");  
            }  
        }     
	}
	
	@SuppressWarnings("unchecked")
	private List<Result> searchAll(String str){
		Integer currPage = 1;
		Integer totalPage = 1;
		List<Result> list = new ArrayList<Result>();
		while(currPage<=totalPage){
			Map<String,String> map = new HashMap<String, String>();
			map.put("keyword", str);
			map.put("page", currPage.toString());
			String result = doPost("http://www.creditchina.gov.cn/channel_record",100000,"UTF-8",map);
			try{
				list.addAll((List<Result>)convertJson(result).get("result"));
				totalPage = Integer.parseInt(convertJson(result).get("totalPage").toString());
				currPage++;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**	
	 * 
	 * search:模拟搜索
	 *
	 * @param str
	 * @param page 
	 * @return    设定文件
	 * @return List<String> 搜索到的结果集
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 * @deprecated
	 */
	@SuppressWarnings("unchecked")
	private List<Result> search(String str,String page){
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("keyword", str);
		map.put("page", page);
		String result = doPost("http://www.creditchina.gov.cn/channel_record",100000,"UTF-8",map);
		List<Result> list = new ArrayList<Result>();
		try{
			list = (List<Result>)convertJson(result).get("result");
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	private Map<String,Object> convertJson(String jsonStr) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(jsonStr);
		JsonNode results = rootNode.path("result").findPath("results");
		Integer currPage = rootNode.path("result").findPath("currentPageNo").asInt();
		Integer totalPage = rootNode.path("result").findPath("totalPageCount").asInt();
		List<Result> list = new ArrayList<Result>();
		for(JsonNode node : results){
			Result result = new Result();
			result.setName(node.path("name").asText());
			result.setEncryStr(node.path("encryStr").asText());
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
	private String doGet(String url, int timeout, String charset){
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
	private String doPost(String url, int timeout, String charset,Map<String,String> params){
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
	 * doAjax:(这里用一句话描述这个方法的作用)
	 *
	 * @param url
	 * @param timeout
	 * @param charset
	 * @param params
	 * @return    设定文件
	 * @return String 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	@SuppressWarnings("deprecation")
	private String doAjax(String url, int timeout, String charset,Map<String,String> params){
		if (charset == null)
	          charset = "utf-8";
	    PostMethod method = new PostMethod(url);
	    String param ="{\"keyword\":"+params.get("keyword")+",\"page\":"+params.get("page")+"}" ;
	    method.setRequestBody(param); 
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
	private String doHttp(HttpMethod result,int timeout, String charset){
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
