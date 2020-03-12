package com.cardnfc.lib.bninfc.tapcashgo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cardnfc.lib.bninfc.tapcashgo.provider.CardProvider;
import com.cardnfc.lib.bninfc.tapcashgo.provider.tapcashgo784a;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class tapcashgo769d {

    @NonNull
    public static String tapcashgo769d_a(@NonNull Context context){
//        DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
//        newInstance.setNamespaceAware(true);
//        DocumentBuilder newDocumentBuilder = newInstance.newDocumentBuilder();
//        Node newDocument = newDocumentBuilder.newDocument();
//        Object createElement = newDocument.createElement("cards");
//        newDocument.appendChild(createElement);
//        Cursor a = tapcashgo784a.cursor(context);
//        while (a.moveToNext()) {
//            a.getInt(a.getColumnIndex("type"));
//            a.getString(a.getColumnIndex("serial"));
//            createElement.appendChild(newDocument.adoptNode(newDocumentBuilder.parse(new InputSource(new StringReader(a.getString(a.getColumnIndex("data"))))).getDocumentElement().cloneNode(true)));
//        }
//        return tapcashgo783m.tapcashgo783m_a(newDocument);
        Object localObject = DocumentBuilderFactory.newInstance();
        ((DocumentBuilderFactory)localObject).setNamespaceAware(true);
        try {
            localObject = ((DocumentBuilderFactory)localObject).newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document localDocument = ((DocumentBuilder)localObject).newDocument();
        Element localElement = localDocument.createElement("cards");
        localDocument.appendChild(localElement);
        Cursor cursor = tapcashgo784a.cursor(context);
        while (cursor.moveToNext())
        {
            cursor.getInt(cursor.getColumnIndex("type"));
            cursor.getString(cursor.getColumnIndex("serial"));
            try {
                localElement.appendChild(localDocument.adoptNode(((DocumentBuilder)localObject).parse(new InputSource(new StringReader(cursor.getString(cursor.getColumnIndex("data"))))).getDocumentElement().cloneNode(true)));
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tapcashgo783m.tapcashgo783m_a(localDocument);
    }

    @Nullable
    public static Uri[] tapcashgo769d_UriArra(@NonNull Context context, String str) {
        Element documentElement = null;
        try {
            documentElement = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(str))).getDocumentElement();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        if (documentElement.getNodeName().equals("card")) {
            return new Uri[]{tapcashgo769d.tapcashgo769d_Uria(context, documentElement)};
        }
        NodeList elementsByTagName = documentElement.getElementsByTagName("card");
        Uri[] uriArr = new Uri[elementsByTagName.getLength()];
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            uriArr[i] = tapcashgo769d.tapcashgo769d_Uria(context, (Element) elementsByTagName.item(i));
        }
        return uriArr;
    }

    @Nullable
    private static Uri tapcashgo769d_Uria(@NonNull Context context, @NonNull Element element) {
        String a = tapcashgo783m.tapcashgo783m_a((Node) element);
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", element.getAttribute("type"));
        contentValues.put("serial", element.getAttribute("id"));
        contentValues.put("data", a);
        contentValues.put("scanned_at", element.getAttribute("scanned_at"));
        return context.getContentResolver().insert(CardProvider.CardProvideruri, contentValues);
    }
}
