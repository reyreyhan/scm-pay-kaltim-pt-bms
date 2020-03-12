package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.util.List;

public interface tapcashgointerface24e {
    void tapcashgointerface24e_voida();

    void tapcashgointerface24e_voida(int i);

    void tapcashgointerface24e_voida(String str) throws ProtocolException;

    void tapcashgointerface24e_voida(String str, String str2);

    @NonNull
    List tapcashgointerface24e_listb();

    int tapcashgointerface24e_intc() throws IOException;

    OutputStream tapcashgointerface24e_outputstreamd() throws IOException;

    InputStream tapcashgointerface24e_inputstreame() throws IOException;

    InputStream tapcashgointerface24e_inputstreamf();
}
