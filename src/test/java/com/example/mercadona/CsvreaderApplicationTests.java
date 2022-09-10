package com.example.mercadona;

import com.example.mercadona.processor.FacturasItemProcessorTests;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CsvreaderApplicationTests {

	@Test
	void main() {
		FacturasItemProcessorTests facturasItemProcessorTests = new FacturasItemProcessorTests();
		facturasItemProcessorTests.setUp();
		facturasItemProcessorTests.testTratamientoFactura();
		facturasItemProcessorTests.testTratamientoFecha();
		facturasItemProcessorTests.testTratamientoImporte();
		facturasItemProcessorTests.testTratamientoFechaKo();
		facturasItemProcessorTests.testTratamientoImporteKo();
	}

}
