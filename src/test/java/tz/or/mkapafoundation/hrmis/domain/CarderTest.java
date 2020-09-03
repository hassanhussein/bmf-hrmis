package tz.or.mkapafoundation.hrmis.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tz.or.mkapafoundation.hrmis.web.rest.TestUtil;

public class CarderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carder.class);
        Carder carder1 = new Carder();
        carder1.setId(1L);
        Carder carder2 = new Carder();
        carder2.setId(carder1.getId());
        assertThat(carder1).isEqualTo(carder2);
        carder2.setId(2L);
        assertThat(carder1).isNotEqualTo(carder2);
        carder1.setId(null);
        assertThat(carder1).isNotEqualTo(carder2);
    }
}
