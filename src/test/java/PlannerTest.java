import com.server.planner.Planner;
import com.server.planner.domain.Machine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.Collections;

import static com.server.planner.domain.TYPE.SERVER;
import static com.server.planner.domain.TYPE.VIRTUAL_MACHINE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PlannerTest {


    @Test
    void testAbleToBuildMachines() {

        Machine.Builder builder = Machine.Builder();

        Machine server = builder
                .withCpu(2)
                .withRam(32)
                .withHDD(100)
                .ofType(SERVER)
                .build();

        assertEquals(2, server.getCpu());
        assertEquals(32, server.getRam());
        assertEquals(100, server.getHdd());
        assertEquals(SERVER, server.getType());

    }


    @Test
    void testPreventMachineConfigurationWithImproperCPU() {

        Machine.Builder builder = Machine.Builder();

        Executable code = () -> builder
                .withCpu(0)
                .withRam(32)
                .withHDD(100)
                .ofType(SERVER)
                .build();

        assertThrows(IllegalArgumentException.class,
                code,
                "CPU must be non zero");
    }

    @Test
    void testPreventMachineConfigurationWithImproperRAM() {

        Machine.Builder builder = Machine.Builder();

        Executable code = () -> builder
                .withCpu(2)
                .withRam(0)
                .withHDD(100)
                .ofType(SERVER)
                .build();

        assertThrows(IllegalArgumentException.class,
                code,
                "RAM must be non zero");
    }

    @Test
    void testPreventMachineConfigurationWithImproperHDD() {

        Machine.Builder builder = Machine.Builder();

        Executable code = () -> builder
                .withCpu(2)
                .withRam(32)
                .withHDD(0)
                .ofType(SERVER)
                .build();

        assertThrows(IllegalArgumentException.class,
                code,
                "HDD must be non zero");
    }


    @Test
    void testPreventMachineConfigurationWithImproperMachineType() {

        Machine.Builder builder = Machine.Builder();

        Executable code = () -> builder
                .withCpu(2)
                .withRam(32)
                .withHDD(100)
                .ofType(null)
                .build();

        assertThrows(IllegalArgumentException.class,
                code,
                "Invalid Machine type");
    }

    @Test
    void testPlannerIsAcceptingOnlyServerToAllocateFrom() {


        Machine virtualMachine = Machine.Builder()
                .withCpu(2)
                .withRam(32)
                .withHDD(100)
                .ofType(VIRTUAL_MACHINE)
                .build();

        Planner planner = new Planner();


        Executable code = () -> planner.calculate(virtualMachine, Collections.singleton(virtualMachine));

        assertThrows(IllegalArgumentException.class,
                code,
                "Machine type must be server");

    }


    @Test
    void testPlannerIsAcceptingNonVirtualMachinesForCalculation() {

        Machine server = Machine.Builder()
                .withCpu(2)
                .withRam(32)
                .withHDD(100)
                .ofType(SERVER)
                .build();


        Planner planner = new Planner();


        Executable code = () -> planner.calculate(server, Collections.singleton(server));

        assertThrows(IllegalArgumentException.class,
                code,
                "Only virtual machines can be created");

    }


    @Test
    void testPlannerIsAcceptingNullableConfigurationsForCalculation() {

        Machine server = Machine.Builder()
                .withCpu(2)
                .withRam(32)
                .withHDD(100)
                .ofType(SERVER)
                .build();


        Planner planner = new Planner();


        Executable code = () -> planner.calculate(server, Collections.singleton(server));

        assertThrows(IllegalArgumentException.class,
                code,
                "Only virtual machines can be created");

    }


    @Test
    void testPlannerIsAcceptingNullableServerForCalculation() {

        Machine server = Machine.Builder()
                .withCpu(2)
                .withRam(32)
                .withHDD(100)
                .ofType(SERVER)
                .build();


        Planner planner = new Planner();


        Executable code = () -> planner.calculate(null, Collections.singleton(server));

        assertThrows(IllegalArgumentException.class,
                code,
                "Machine configuration cannot be null");

    }


    @Test
    void testPlannerIsAbleToIdentifyPossibleConfigurations() {


        Machine server = Machine.Builder()
                .withCpu(2)
                .withRam(32)
                .withHDD(100)
                .ofType(SERVER)
                .build();


        Machine virtualMachine1 = Machine.Builder()
                .withCpu(1)
                .withRam(16)
                .withHDD(10)
                .ofType(VIRTUAL_MACHINE)
                .build();

        Machine virtualMachine2 = Machine.Builder()
                .withCpu(1)
                .withRam(16)
                .withHDD(10)
                .ofType(VIRTUAL_MACHINE)
                .build();


        Machine virtualMachine3 = Machine.Builder()
                .withCpu(2)
                .withRam(32)
                .withHDD(100)
                .ofType(VIRTUAL_MACHINE)
                .build();


        Planner planner = new Planner();

        long possibleVirtualMachines = planner.calculate(server, Arrays.asList(virtualMachine1, virtualMachine2, virtualMachine3));

        assertEquals(2, possibleVirtualMachines);
    }
}
