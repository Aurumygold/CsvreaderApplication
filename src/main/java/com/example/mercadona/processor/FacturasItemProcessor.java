package com.example.mercadona.processor;

import com.example.mercadona.model.Factura;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ParseException;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class FacturasItemProcessor implements ItemProcessor<Factura, Factura> {

    private static final Logger LOG = LoggerFactory.getLogger(FacturasItemProcessor.class);

    @Override
    public Factura process(Factura item) throws Exception {

        String s_fecha;
        String s_importe;
        try {
            SimpleDateFormat in_f_formato = new SimpleDateFormat("mm/dd/yyyy");
            SimpleDateFormat out_f_formato = new SimpleDateFormat("dd/mm/yyyy");
            java.util.Date f_fecha = in_f_formato.parse(item.getFecha());
            s_fecha = out_f_formato.format(f_fecha);
            //LOG.info("Item "+item+ " ha cogido la fecha" +f_fecha);
        }
        catch (ParseException ex){
            s_fecha = item.getFecha();
            LOG.info("Item "+item+ " a fallado la conversion de fecha");
        }

        try {
            NumberFormat in_d_formato = NumberFormat.getInstance(Locale.FRANCE);
            s_importe = in_d_formato.parse(item.getImporte()).toString();
            //LOG.info("Item "+item+ " ha cogido la numeracion" +s_importe);
        }
        catch (ParseException ex){
            s_importe = item.getImporte();
            LOG.info("Item "+item+ " a fallado la conversion numerica");
        }

        Integer identificador = item.getIdentificador();
        Integer identificadorlegacy = item.getIdentificadorlegacy();
        String nombre = item.getNombre().toUpperCase();
        String fecha = s_fecha;
        String importe = s_importe;


        Factura factura = new Factura(identificador, identificadorlegacy, nombre, fecha, importe);

        //LOG.info("Tramitando "+item+ " a "+  factura);

        return  factura;

    }
}
