package tz.or.mkapafoundation.hrmis.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tz.or.mkapafoundation.hrmis.web.rest.TestUtil;

public class CarderDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarderDTO.class);
        CarderDTO carderDTO1 = new CarderDTO();
        carderDTO1.setId(1L);
        CarderDTO carderDTO2 = new CarderDTO();
        assertThat(carderDTO1).isNotEqualTo(carderDTO2);
        carderDTO2.setId(carderDTO1.getId());
        assertThat(carderDTO1).isEqualTo(carderDTO2);
        carderDTO2.setId(2L);
        assertThat(carderDTO1).isNotEqualTo(carderDTO2);
        carderDTO1.setId(null);
        assertThat(carderDTO1).isNotEqualTo(carderDTO2);
    }
}
