/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import modelo.Sala;
import modelo.Usuario;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author 1001001222
 */
public class SalaDAOXML extends SalaDAO{

    public boolean registrarSala(Sala sala) {
        boolean retornar=false;
        try {
            String filepath = ( "Salas.xml" );
            DocumentBuilderFactory docFactory = DocumentBuilderFactory .newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = null ;
            doc = docBuilder.parse(filepath);
            doc.getDocumentElement().normalize();
            NodeList lista=doc.getElementsByTagName("sala");
            for(int temp=0;temp<lista.getLength();temp++) {
                Node nodo=lista.item(temp);
                //System. out. println("temp"+temp) ;
                if ( nodo. getNodeType () ==Node.ELEMENT_NODE ) {
                    Element element = ( Element )nodo;
                    if(element.getElementsByTagName("identificador").item(0).getTextContent().equals(sala.getIdentificador())){
                        retornar=true;
                    }
                }
            }
            if(retornar){
                return false;
            }else{
                Element user=doc.createElement( "sala" );
                Element nombre= doc.createElement( "identificador" );
                Text valor1=doc.createTextNode(sala.getIdentificador());
                Element contra= doc.createElement( "ubicacion" );
                Text valor2=doc.createTextNode(sala.getUbicacion());
                Element capa= doc.createElement( "capacidad" );
                Text valor3=doc.createTextNode(Integer.toString(sala.getCapacidad()));
                
                NodeList nodoRaiz= doc.getChildNodes(); 
                nodoRaiz.item(0).appendChild(user);
                user.appendChild(nombre);
                nombre.appendChild(valor1);
                user.appendChild(contra);
                contra.appendChild(valor2);
                user.appendChild(capa);
                capa.appendChild(valor3);
            }
            guardarConFormato(doc,filepath);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(UsuarioDAOXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(UsuarioDAOXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UsuarioDAOXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public ArrayList<Sala> cargarSalas() {
        ArrayList<Sala> salas=new ArrayList<Sala>();
        try {
            File archivo = new File( "Salas.xml" ) ;
            DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance () ;
            DocumentBuilder documentBuilder =dbf.newDocumentBuilder() ;
            Document document =documentBuilder.parse (archivo ) ;
            document. getDocumentElement().normalize();
            //System.out. println("Elemento raiz:"+document.getDocumentElement().getNodeName());
            NodeList lista =document. getElementsByTagName ( "sala" );
            for ( int temp = 0; temp < lista. getLength() ; temp++ ) {
                Node nodo = lista. item(temp );
                //System. out. println( "Usuario:" +nodo. getNodeName ()) ;
                if ( nodo. getNodeType () ==Node. ELEMENT_NODE ) {
                    Element element = ( Element )nodo;
                    int capacidad=Integer.parseInt(element.getElementsByTagName ( "capacidad" ) . item(0 ) .getTextContent ());
                    salas.add(new Sala(element.getElementsByTagName ( "identificador" ) . item(0 ) .getTextContent ()
                    ,element. getElementsByTagName ( "ubicacion" ) . item(0 ) .getTextContent (),capacidad));
                }
            }
        } catch (Exception e ) {
            e. printStackTrace() ;
        }
        return salas;
    }
    
    public void guardarConFormato(Document document, String URI){
        try {
            TransformerFactory transFact=TransformerFactory.newInstance();
            transFact.setAttribute("indent-number", new Integer(3));
            Transformer trans = transFact.newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            StringWriter sw = new StringWriter();
            StreamResult sr = new StreamResult(sw);
            DOMSource domSource = new DOMSource(document);
            trans.transform(domSource, sr);
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(URI));
                writer.println(sw.toString());
                writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
