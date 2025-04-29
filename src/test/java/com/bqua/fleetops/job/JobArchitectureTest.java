package com.bqua.fleetops.job;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.bqua.fleetops.job")
public class JobArchitectureTest {


    @ArchTest
    static final ArchRule domain_should_not_access_adapter =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().accessClassesThat().resideInAPackage("..adapter..");

    @ArchTest
    static final ArchRule domain_should_not_access_application =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().accessClassesThat().resideInAPackage("..application..");

    @ArchTest
    static final ArchRule domain_should_not_depend_on_external_infrastructure =
            classes()
                    .that().resideInAPackage("..job.domain..")
                    .should().onlyDependOnClassesThat()
                    .resideOutsideOfPackage("..infrastructure.."); // 외부 infra 금지

    @ArchTest
    static final ArchRule application_should_not_access_adapter =
            noClasses()
                    .that().resideInAPackage("..application..")
                    .should().accessClassesThat().resideInAPackage("..adapter..");

    @ArchTest
    static final ArchRule application_should_not_depend_on_external_infrastructure =
            classes()
                    .that().resideInAPackage("..job.domain..")
                    .should().onlyDependOnClassesThat()
                    .resideOutsideOfPackage("..infrastructure.."); // 외부 infra 금지


}