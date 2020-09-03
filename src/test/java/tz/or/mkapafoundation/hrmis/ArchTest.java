package tz.or.mkapafoundation.hrmis;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("tz.or.mkapafoundation.hrmis");

        noClasses()
            .that()
                .resideInAnyPackage("tz.or.mkapafoundation.hrmis.service..")
            .or()
                .resideInAnyPackage("tz.or.mkapafoundation.hrmis.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..tz.or.mkapafoundation.hrmis.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
