package com.github.igocoelho;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import static org.junit.jupiter.api.condition.JRE.*;
import static org.junit.jupiter.api.condition.OS.MAC;

public class ConditionalExecutionDemo {

    @Test
    @EnabledOnOs(architectures = "aarch64")
    void onAarch64() {
        // ...
    }

    @Test
    @DisabledOnOs(architectures = "x86_64")
    void notOnX86_64() {
        // ...
    }

    @Test
    @EnabledOnOs(value = MAC, architectures = "aarch64")
    void onNewMacs() {
        // ...
    }

    @Test
    @DisabledOnOs(value = MAC, architectures = "aarch64")
    void notOnNewMacs() {
        // ...
    }

    @Test
    @EnabledOnJre(JAVA_17)
    void onlyOnJava17() {
        // ...
    }

    @Test
    @EnabledOnJre({JAVA_17, JAVA_21})
    void onJava17And21() {
        // ...
    }

    @Test
    @EnabledForJreRange(min = JAVA_9, max = JAVA_11)
    void fromJava9To11() {
        // ...
    }

    @Test
    @EnabledForJreRange(min = JAVA_9)
    void onJava9AndHigher() {
        // ...
    }

    @Test
    @EnabledForJreRange(max = JAVA_11)
    void fromJava8To11() {
        // ...
    }

    @Test
    @DisabledOnJre(JAVA_9)
    void notOnJava9() {
        // ...
    }

    @Test
    @DisabledForJreRange(min = JAVA_9, max = JAVA_11)
    void notFromJava9To11() {
        // ...
    }

    @Test
    @DisabledForJreRange(min = JAVA_9)
    void notOnJava9AndHigher() {
        // ...
    }

    @Test
    @DisabledForJreRange(max = JAVA_11)
    void notFromJava8To11() {
        // ...
    }

    @Test
    @EnabledInNativeImage
    void onlyWithinNativeImage() {
        // ...
    }

    @Test
    @DisabledInNativeImage
    void neverWithinNativeImage() {
        // ...
    }

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void onlyOn64BitArchitectures() {
        // ...
    }

    @Test
    @DisabledIfSystemProperty(named = "ci-server", matches = "true")
    void notOnCiServer() {
        // ...
    }

    // Environment Variable Conditions
    @Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "staging-server")
    void onlyOnStagingServer() {
        // ...
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "ENV", matches = ".*development.*")
    void notOnDeveloperWorkstation() {
        // ...
    }

    @Test
    @EnabledIf("customCondition")
    void enabled() {
        // ...
    }

    @Test
    @DisabledIf("customCondition")
    void disabled() {
        // ...
    }

    boolean customCondition() {
        return true;
    }
}
