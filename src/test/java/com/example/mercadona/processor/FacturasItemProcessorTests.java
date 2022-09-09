package com.example.mercadona.processor;

import com.example.mercadona.model.Factura;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    // Test que prueban la funcionalidad
    @Test
    public void testTratamientoFecha() {

        assertEquals("15/12/2022", facturasItemProcessor.parseFecha("12/15/2022"));

    }
    @Test
    public void testTratamientoImporte() {

        assertEquals("1254.06", facturasItemProcessor.parseImporte("1254,06"));
        assertEquals("54.06", facturasItemProcessor.parseImporte("54,06"));

    }
    @Test
    public void testTratamientoFactura() {

        when(next.getIdentificadorlegacy()).thenReturn(20220045);
        when(next.getNombre()).thenReturn("Juan");
        when(next.getFecha()).thenReturn("12/15/2022");
        when(next.getImporte()).thenReturn("54,06");

        next2 = facturasItemProcessor.process(next);
        assertEquals("54.06", next2.getImporte());
        assertEquals("15/12/2022", next2.getFecha());

    }

    // Test de errores

    @Test
    public void testTratamientoImporteKo() {

        assertNotEquals("1254.06", facturasItemProcessor.parseImporte("1.254,06"));
        assertNotEquals("1,254.06", facturasItemProcessor.parseImporte("1.254,06"));
        assertNotEquals("54.06", facturasItemProcessor.parseImporte("54.06"));
        assertNotEquals("54.06", facturasItemProcessor.parseImporte("kjg"));

    }
    @Test
    public void testTratamientoFechaKo() {

        assertNotEquals("15/12/2022", facturasItemProcessor.parseFecha("15/12/2022"));
        assertNotEquals("15/12/2022", facturasItemProcessor.parseFecha("15/12/2022 20:55"));
        assertNotEquals("15/12/2022", facturasItemProcessor.parseFecha("15-12-2022"));
        assertNotEquals("15/12/2022", facturasItemProcessor.parseFecha("12-15-2022"));

    }
}
