package com.example.mercadona.processor;

import com.example.mercadona.model.Factura;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class FacturasItemProcessor implements ItemProcessor<Factura, Factura> {

    private static final Logger LOG = LoggerFactory.getLogger(FacturasItemProcessor.class);

    @Override
    public Factura process(Factura item) {

        Integer identificador = item.getIdentificador();
        Integer identificadorlegacy = item.getIdentificadorlegacy();
        String nombre = item.getNombre().toUpperCase();
        String fecha = parseFecha(item.getFecha());
        String importe = parseImporte(item.getImporte());

        //LOG.info("Tramitando "+item+ " a "+  factura);

        return new Factura(identificador, identificadorlegacy, nombre, fecha, importe);

    }

    public String parseFecha (String in_fecha){
        String s_fecha;
        try {
            SimpleDateFormat in_f_formato = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat out_f_formato = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date f_fecha = in_f_formato.parse(in_fecha);
            s_fecha = out_f_formato.format(f_fecha);
            //LOG.info("Item "+item+ " ha cogido la fecha" +f_fecha);
        }
        catch (java.text.ParseException  ex){
            s_fecha = in_fecha;
            LOG.info("Item "+ in_fecha + " a fallado la conversion de fecha");
        }
        return s_fecha;
    }

    public String parseImporte (String in_importe){
        String s_importe;
        try {
            NumberFormat in_d_formato = NumberFormat.getInstance(Locale.FRANCE);
            s_importe = in_d_formato.parse(in_importe).toString();
            //LOG.info("Item "+item+ " ha cogido la numeracion" +s_importe);
        }
        catch (java.text.ParseException ex){
            s_importe = in_importe;
            LOG.info("Item "+in_importe+ " a fallado la conversion numerica");
        }
        return s_importe;
    }
}
