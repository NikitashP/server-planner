package com.server.planner;

import com.server.planner.domain.Machine;
import com.server.planner.domain.TYPE;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class Planner {

    public long calculate(Machine server, Collection<Machine> virtualMachines) {

        long possibleVirtualMachineAllocations=0;

        if(Objects.isNull(server)){
            throw new IllegalArgumentException("Machine configuration cannot be null");
        }

        if(server.getType()!= TYPE.SERVER){
            throw new IllegalArgumentException("Machine type must be server");
        }

        Collection<Machine> machines = Optional.ofNullable(virtualMachines).orElseThrow(() -> new IllegalArgumentException("Virtual machines cannot be null"));

        long nonVirtualMachines = machines.stream().filter(x -> x.getType() != TYPE.VIRTUAL_MACHINE).count();

        if(nonVirtualMachines>0){
            throw new IllegalArgumentException("Only virtual machines can be created");
        }

        int availableCpu = server.getCpu();
        int availableHdd = server.getHdd();
        int availableRam = server.getRam();

        // first fit strategy. allocate to one that can be allocated

        for (Machine virtualMachine: virtualMachines) {

            int necessaryCpu = virtualMachine.getCpu();
            int necessaryHdd = virtualMachine.getHdd();
            int necessaryRam = virtualMachine.getRam();
            if(availableCpu >= necessaryCpu && availableHdd>=necessaryHdd && availableRam>=necessaryRam){
                availableCpu-=necessaryCpu;
                availableHdd-=necessaryHdd;
                availableRam-=necessaryRam;
                possibleVirtualMachineAllocations++;
            }
        }

        return possibleVirtualMachineAllocations;
    }
}
