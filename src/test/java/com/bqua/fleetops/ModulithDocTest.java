package com.bqua.fleetops;

import com.tngtech.archunit.core.domain.JavaClass;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ModulithDocTest {

    @Test
    void writeDocs() {
        var modules = ApplicationModules.of(
                FleetOpsApplication.class,
                JavaClass.Predicates.resideInAPackage("com.bqua.fleetops.job.domain.entity.job.enums")
                        .or(JavaClass.Predicates.resideInAPackage("com.bqua.fleetops.job.domain.entity.jobexecution.enums"))
        );
        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml()
                .writeDocumentation();
        modules.verify();
    }
}
