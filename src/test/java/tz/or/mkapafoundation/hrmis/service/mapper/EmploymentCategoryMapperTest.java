package tz.or.mkapafoundation.hrmis.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmploymentCategoryMapperTest {

    private EmploymentCategoryMapper employmentCategoryMapper;

    @BeforeEach
    public void setUp() {
        employmentCategoryMapper = new EmploymentCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(employmentCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(employmentCategoryMapper.fromId(null)).isNull();
    }
}
