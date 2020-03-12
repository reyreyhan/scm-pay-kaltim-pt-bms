package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;

import java.io.InputStream;

public interface tapcashgointerface30a {
    public static final String[] tapcashgointerface30a_stringarra = new String[]{"START_DOCUMENT", "END_DOCUMENT", "START_TAG", "END_TAG", "TEXT", "CDSECT", "ENTITY_REF", "IGNORABLE_WHITESPACE", "PROCESSING_INSTRUCTION", "COMMENT", "DOCDECL"};

    int tapcashgointerface30a_inta();

    int tapcashgointerface30a_inta(int i);

    @NonNull
    String tapcashgointerface30a_stringa(String str);

    void tapcashgointerface30a_voida(int i, String str, String str2);

    void tapcashgointerface30a_voida(InputStream inputStream, String str);

    void tapcashgointerface30a_voida(String str, boolean z);

    int tapcashgointerface30a_intb();

    @NonNull
    String tapcashgointerface30a_stringb(int i);

    int tapcashgointerface30a_intc();

    @NonNull
    String tapcashgointerface30a_stringc(int i);

    @NonNull
    String tapcashgointerface30a_stringd();

    @NonNull
    String tapcashgointerface30a_stringd(int i);

    @NonNull
    String tapcashgointerface30a_stringe();

    @NonNull
    String tapcashgointerface30a_stringe(int i);

    @NonNull
    String tapcashgointerface30a_strngf();

    boolean tapcashgointerface30a_booleang();

    int getAttributeCount();

    @NonNull
    String getAttributeName(int i);

    @NonNull
    String getAttributeValue(int i);

    @NonNull
    String getAttributeValue(String str, String str2);

    @NonNull
    String getPositionDescription();

    int tapcashgointerface30a_inth();

    int tapcashgointerface30a_inti();

    int tapcashgointerface30a_intj();

    int tapcashgointerface30a_intk();

    @NonNull
    String tapcashgointerface30a_stringl();
}
