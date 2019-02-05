package com.server.planner.domain;

import java.util.Objects;

public class Machine {

    private final int cpu;
    private final int ram;
    private final int hdd;
    private final TYPE type;

    private static final Builder builder=new Builder();

    private Machine(int cpu, int ram, int hdd, TYPE type) {

        this.cpu = cpu;
        this.ram = ram;
        this.hdd = hdd;
        this.type = type;
    }



    public int getCpu() {
        return cpu;
    }

    public int getRam() {
        return ram;
    }

    public int getHdd() {
        return hdd;
    }


    public static Machine.Builder Builder() {
        return builder;
    }

    public TYPE getType() {
        return type;
    }


    public static class Builder {
        private int cpu;
        private int ram;
        private int hdd;
        private TYPE type;

        public Builder withCpu(int cores) {
            if(cores<=0){
                throw new IllegalArgumentException("CPU must be non zero");
            }
            this.cpu =cores;
            return this;
        }

        public Builder withRam(int ramCapacity) {
            if(ramCapacity<=0){
                throw new IllegalArgumentException("RAM must be non zero");
            }
            this.ram=ramCapacity;
            return this;
        }

        public Builder withHDD(int hddCapacity) {
            if(hddCapacity<=0){
                throw new IllegalArgumentException("HDD must be non zero");
            }
            this.hdd=hddCapacity;
            return this;
        }

        public Builder ofType(TYPE type) {
            if(Objects.isNull(type)){
                throw new IllegalArgumentException("Invalid Machine type");

            }
            this.type=type;
            return this;
        }

        public Machine build() {
            return new Machine(cpu,ram,hdd,type);
        }
    }
}
