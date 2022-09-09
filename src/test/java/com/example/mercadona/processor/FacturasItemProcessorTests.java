package com.example.mercadona.processor;

import com.example.mercadona.model.Factura;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FacturasItemProcessorTests {

    @Rule
    public ExpectedException expectedException ;
    @Mock
    private Factura next;
    private Factura next2;
    private FacturasItemProcessor facturasItemProcessor;

    @Before
    public void setUp() {

        next = mock(Factura.class);
        next2 = new Factura();

        facturasItemProcessor = new FacturasItemProcessor();
    }

    @Test
    public void testTratamientoFecha() {

        assertEquals("15/12/2022", facturasItemProcessor.parseFecha("12/15/2022"));

    }
    @Test
    public void testTratamientoImporte() {

        assertEquals("54.06", facturasItemProcessor.parseImporte("54,06"));

    }
    @Test
    public void testTratamientoFactura() throws Exception{

        when(next.getIdentificadorlegacy()).thenReturn(20220045);
        when(next.getNombre()).thenReturn("Juan");
        when(next.getFecha()).thenReturn("12/15/2022");
        when(next.getImporte()).thenReturn("54,06");

        next2 = facturasItemProcessor.process(next);
        assertEquals("54.06", next2.getImporte());
        assertEquals("15/12/2022", next2.getFecha());

    }

}
