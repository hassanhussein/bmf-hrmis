package tz.or.mkapafoundation.hrmis.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tz.or.mkapafoundation.hrmis.web.rest.TestUtil;

public class GeographicLevelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeographicLevel.class);
        GeographicLevel geographicLevel1 = new GeographicLevel();
        geographicLevel1.setId(1L);
        GeographicLevel geographicLevel2 = new GeographicLevel();
        geographicLevel2.setId(geographicLevel1.getId());
        assertThat(geographicLevel1).isEqualTo(geographicLevel2);
        geographicLevel2.setId(2L);
        assertThat(geographicLevel1).isNotEqualTo(geographicLevel2);
        geographicLevel1.setId(null);
        assertThat(geographicLevel1).isNotEqualTo(geographicLevel2);
    }
}
