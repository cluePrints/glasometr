package org.chesno.glasometr.parse;

import java.io.InputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;

import org.apache.xpath.XPathAPI;
import org.ccil.cowan.tagsoup.Parser;
import org.chesno.glasometr.domain.Protocol;
import org.chesno.glasometr.domain.Vote;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

public class ProtocolParser
{
	public Protocol parse(InputStream html) throws Exception
	{
		Node root = initDom(html);
		NodeIterator nl = XPathAPI.selectNodeIterator(root, "//tr");
		Node n;
		Protocol result = new Protocol();
		while ((n = nl.nextNode()) != null)
		{
			NodeList cells = XPathAPI.selectNodeList(n,
					".//td[not(@colspan>1)]");
			String name = null;
			Vote vote = null;
			for (int i = 0; i < cells.getLength(); i++)
			{
				Node node = cells.item(i);
				String text = node.getTextContent();
				String classAttribute = String.valueOf(node.getAttributes().getNamedItem("class"));
				if (node.hasAttributes() && "class=\"hcol1\"".equals(classAttribute))
				{
					name = text;
				} else if ("За".equalsIgnoreCase(text))
				{
					vote = Vote.Yes;
				} else if ("Проти".equalsIgnoreCase(text))
				{
					vote = Vote.Yes;
				} else if (text.startsWith("Відсут"))
				{
					vote = Vote.WasNotPresent;
				} else if (text.startsWith("Не голо"))
				{
					vote = Vote.NotVoted;
				} else if (text.startsWith("Утрима"))
				{
					vote = Vote.Refrained;
				}
				
				if (name != null && vote != null)
				{
					System.out.println("Adding "+vote+" for "+name);
					result.getVotes().put(name, vote);
					name = null;
					vote = null;				
				}

			}
		}

		return result;
	}

	private Node initDom(InputStream html) throws SAXNotRecognizedException,
			SAXNotSupportedException, TransformerConfigurationException,
			TransformerFactoryConfigurationError, TransformerException
	{
		XMLReader reader = new Parser();
		reader.setFeature(Parser.namespacesFeature, false);
		reader.setFeature(Parser.namespacePrefixesFeature, false);

		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();

		DOMResult dom = new DOMResult();
		transformer
				.transform(new SAXSource(reader, new InputSource(html)), dom);
		Node root = dom.getNode();
		return root;
	}
}