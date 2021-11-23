/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import modelo.Usuario;

import java.io.File;
import org.w3c.dom.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
/**
 *
 * @author 1001001222
 */
public class UsuarioDAOXML extends UsuarioDAO{

    public UsuarioDAOXML(){
        
    }
    
    @Override
    public Usuario iniciarSesion(Usuario usuario) {
        ArrayList<Usuario> usuarios=cargarListaUsuarios();
        boolean esta=false;
        for(int i=0;i<usuarios.size();i++){
            if(usuario.equals(usuarios.get(i))){
                esta=true;
                break;
            }
        }
        if(esta){
            return usuario;
        }else{
            return null;
        }
    }

    @Override
    public void restaurarContraseña(String nombreUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Usuario> cargarListaUsuarios() {
        ArrayList<Usuario> usuarios=new ArrayList<Usuario>();
        try {
            File archivo = new File( "Usuarios.xml" ) ;
            DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance () ;
            DocumentBuilder documentBuilder =dbf.newDocumentBuilder() ;
            Document document =documentBuilder.parse (archivo ) ;
            document. getDocumentElement().normalize();
            //System.out. println("Elemento raiz:"+document.getDocumentElement().getNodeName());
            NodeList lista =document. getElementsByTagName ( "usuario" );
            for ( int temp = 0; temp < lista. getLength() ; temp++ ) {
                Node nodo = lista. item(temp );
                //System. out. println( "Usuario:" +nodo. getNodeName ()) ;
                if ( nodo. getNodeType () ==Node. ELEMENT_NODE ) {
                    Element element = ( Element )nodo;
                    usuarios.add(new Usuario(element.getElementsByTagName ( "nombre" ) . item(0 ) .getTextContent ()
                    ,element. getElementsByTagName ( "contrasena" ) . item(0 ) .getTextContent ()));
                }
            }
        } catch (Exception e ) {
            e. printStackTrace() ;
        }
        return usuarios;
    }

    @Override
    public boolean cambiarContraseña(String nombreUsuario, String contrasena, String contrasenaNueva) {
        return true;
    }

    @Override
    public boolean registrarUsuario(Usuario usuario) {
        boolean retornar=false;
        try {
            String filepath = ( "Usuarios.xml" );
            DocumentBuilderFactory docFactory = DocumentBuilderFactory .newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = null ;
            doc = docBuilder.parse(filepath);
            doc.getDocumentElement().normalize();
            NodeList lista=doc.getElementsByTagName("usuario");
            for(int temp=0;temp<lista.getLength();temp++) {
                Node nodo=lista.item(temp);
                //System. out. println("temp"+temp) ;
                if ( nodo. getNodeType () ==Node.ELEMENT_NODE ) {
                    Element element = ( Element )nodo;
                    if(element.getElementsByTagName("nombre").item(0).getTextContent().equals(usuario.getNombre())){
                        retornar=true;
                    }
                }
            }
            if(retornar){
                return false;
            }else{
                Element user=doc.createElement( "usuario" );
                Element nombre= doc.createElement( "nombre" );
                Text valor1=doc.createTextNode(usuario.getNombre());
                Element contra= doc.createElement( "contrasena" );
                Text valor2=doc.createTextNode(usuario.getContrasena());
                
                NodeList nodoRaiz= doc.getChildNodes(); 
                nodoRaiz.item(0).appendChild(user);
                user.appendChild(nombre);
                nombre.appendChild(valor1);
                user.appendChild(contra);
                contra.appendChild(valor2);
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
