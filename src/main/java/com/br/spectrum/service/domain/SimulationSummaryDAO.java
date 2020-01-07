package com.br.spectrum.service.domain;

import com.br.spectrum.service.EventManagement.ControlPlane;
import com.br.spectrum.service.EventManagement.CriticalBPEventSnapshot;
import com.br.spectrum.service.EventManagement.CriticalFragmentationEventSnapshot;
import com.br.spectrum.service.EventManagement.SimulationInstanceSummaryWithControlPlane;

import java.util.*;

public class SimulationSummaryDAO {
    private String id;
    private SimulationInstanceSummaryStatistics statistics;

    private ArrayList<CriticalBPEventSnapshot> criticalBPEventSnapshots;
    private ArrayList<CriticalFragmentationEventSnapshot> criticalFragmentationEventSnapshots;
    private ArrayList<SimulationInstanceSummary> simulationInstanceSummaries;
    private int cycleNum;
    private int startLoad;
    private int loadStep;
    private List<CallClassGeneralStatistics> blockedCallsAmountPerClass;

    public SimulationSummaryDAO(SimulationSummary simulationSummary) {
        this.id = simulationSummary.getId();
        this.criticalBPEventSnapshots = new ArrayList<>();
        this.criticalFragmentationEventSnapshots = new ArrayList<>();
        this.simulationInstanceSummaries = new ArrayList<>();
        this.statistics = new SimulationInstanceSummaryStatistics();


        List<SimulationInstanceSummaryWithControlPlane> sortedInstances = new ArrayList<>( simulationSummary.getSimulationInstanceSummaryHashMap().values());
        sortedInstances.sort(new Comparator<SimulationInstanceSummaryWithControlPlane>() {
            @Override
            public int compare(SimulationInstanceSummaryWithControlPlane o1, SimulationInstanceSummaryWithControlPlane o2) {
                return o1.getSimulationInstanceSummary().getLoad() <= o2.getSimulationInstanceSummary().getLoad() ? -1 : 1;
            }
        });

        sortedInstances.forEach(simulationInstanceSummaryWithControlPlane -> {
            SimulationInstanceSummary instanceSummary = simulationInstanceSummaryWithControlPlane.getSimulationInstanceSummary();
            ControlPlane controlPlane = simulationInstanceSummaryWithControlPlane.getControlPlane();
            CriticalBPEventSnapshot criticalBPEventSnapshot = controlPlane.getMostCriticalBPEvent();
            CriticalFragmentationEventSnapshot criticalFragmentationEventSnapshot = controlPlane.getMostCriticalFragmentationEvent();

            if(criticalBPEventSnapshot != null){
                criticalBPEventSnapshot.setLoad(instanceSummary.getLoad());
                this.criticalBPEventSnapshots.add(controlPlane.getMostCriticalBPEvent());
            }
            if(criticalFragmentationEventSnapshot != null){
                criticalFragmentationEventSnapshot.setLoad(instanceSummary.getLoad());
                this.criticalFragmentationEventSnapshots.add(controlPlane.getMostCriticalFragmentationEvent());
            }
            instanceSummary.calculateInfos();
            this.simulationInstanceSummaries.add(instanceSummary);

            this.statistics.setTotalAllocatedAmount(this.statistics.getTotalAllocatedAmount() + instanceSummary.getStatistics().getTotalAllocatedAmount());
            this.statistics.setTotalBlockedAmount(this.statistics.getTotalBlockedAmount() + instanceSummary.getStatistics().getTotalBlockedAmount());
            this.statistics.setTotalSuccessAmount(this.statistics.getTotalSuccessAmount() + instanceSummary.getStatistics().getTotalSuccessAmount());
            this.statistics.setTotalDelayedAmount(this.statistics.getTotalDelayedAmount() + instanceSummary.getStatistics().getTotalDelayedAmount());
            this.statistics.setTotalDroppedAmount(this.statistics.getTotalDroppedAmount() + instanceSummary.getStatistics().getTotalDroppedAmount());
            this.statistics.setTotalBlockedBandwidthAmount(this.statistics.getTotalBlockedBandwidthAmount() + instanceSummary.getStatistics().getTotalBlockedBandwidthAmount());
            this.statistics.setTotalBandwidthAmount(this.statistics.getTotalBandwidthAmount() + instanceSummary.getStatistics().getTotalBandwidthAmount());

            this.cycleNum = instanceSummary.getCycleNum();

            int cycleTotalAllocatedAmount = 0;
            int cycleTotalBlockedAmount = 0;
            int cycleTotalDroppedAmount = 0;
            int cycleTotalDelayedAmount = 0;
            int cycleTotalSuccessAmount = 0;
            int cycleTotalBandwidthAmount = 0;
            int cycleTotalBlockedBandwidthAmount =0;
            int cycleTotalCallsAmount =0;

            for (int i = 0; i< cycleNum; i++){
                cycleTotalAllocatedAmount+=(int)getIndexValueFromArrayList(instanceSummary.getStatistics().getAllocatedAmountPerCycle(), i);
                cycleTotalBlockedAmount+=(int)getIndexValueFromArrayList(instanceSummary.getStatistics().getBlockedAmountPerCycle(), i);
                cycleTotalDroppedAmount+=(int)getIndexValueFromArrayList(instanceSummary.getStatistics().getDroppedAmountPerCycle(), i);
                cycleTotalDelayedAmount+=(int)getIndexValueFromArrayList(instanceSummary.getStatistics().getDelayedAmountPerCycle(), i);
                cycleTotalSuccessAmount+=(int)getIndexValueFromArrayList(instanceSummary.getStatistics().getSuccessAmountPerCycle(), i);
                cycleTotalBandwidthAmount+=getIndexValueFromArrayList(instanceSummary.getStatistics().getTotalBandwidthAmountPerCycle(), i);
                cycleTotalBlockedBandwidthAmount+=getIndexValueFromArrayList(instanceSummary.getStatistics().getTotalBlockedBandwidthAmountPerCycle(), i);
                cycleTotalCallsAmount+=(int)getIndexValueFromArrayList(instanceSummary.getStatistics().getSuccessAmountPerCycle(), i) + (int)getIndexValueFromArrayList(instanceSummary.getStatistics().getBlockedAmountPerCycle(), i);

            }
            this.statistics.getAllocatedAmountPerCycle().add(cycleTotalAllocatedAmount);
            this.statistics.getBlockedAmountPerCycle().add(cycleTotalBlockedAmount);
            this.statistics.getDelayedAmountPerCycle().add(cycleTotalDelayedAmount);
            this.statistics.getDroppedAmountPerCycle().add(cycleTotalDroppedAmount);
            this.statistics.getSuccessAmountPerCycle().add(cycleTotalSuccessAmount);
            this.statistics.getTotalAmountPerCycle().add(cycleTotalCallsAmount);
            this.statistics.getTotalBandwidthAmountPerCycle().add(cycleTotalBandwidthAmount);
            this.statistics.getTotalBlockedBandwidthAmountPerCycle().add(cycleTotalBlockedBandwidthAmount);
        });

        this.statistics.calculateStatistics();

        HashMap<String, CallClassGeneralStatistics> callClassGeneralStatisticsHashMap = new HashMap<>();

        this.simulationInstanceSummaries.forEach(simulationInstanceSummary -> {
            simulationInstanceSummary.calculateBPPerClass();
            simulationInstanceSummary.getClassSummaries().forEach(classSummary -> {
                if(callClassGeneralStatisticsHashMap.containsKey(classSummary.getClassId())){
                    callClassGeneralStatisticsHashMap.get(classSummary.getClassId()).setBlockedAmount(callClassGeneralStatisticsHashMap.get(classSummary.getClassId()).getBlockedAmount() + classSummary.getStatistics().getTotalBlockedAmount());
                }else{
                    callClassGeneralStatisticsHashMap.put(classSummary.getClassId(), new CallClassGeneralStatistics(classSummary.getClassName(), classSummary.getClassId(), classSummary.getColor(), classSummary.getStatistics().getTotalBlockedAmount()));
                }
            });
        });
        this.blockedCallsAmountPerClass = new ArrayList<>();
        this.blockedCallsAmountPerClass.addAll(callClassGeneralStatisticsHashMap.values());
    }

    public List<CallClassGeneralStatistics> getBlockedCallsAmountPerClass() {
        return blockedCallsAmountPerClass;
    }

    public void setBlockedCallsAmountPerClass(List<CallClassGeneralStatistics> blockedCallsAmountPerClass) {
        this.blockedCallsAmountPerClass = blockedCallsAmountPerClass;
    }

    public double getIndexValueFromArrayList(ArrayList<Integer> list, int index){
        Integer[] listArray = new Integer[list.size()];
        list.toArray(listArray);
        return listArray[index];
    }

    public ArrayList<CriticalFragmentationEventSnapshot> getCriticalFragmentationEventSnapshots() {
        return criticalFragmentationEventSnapshots;
    }

    public void setCriticalFragmentationEventSnapshots(ArrayList<CriticalFragmentationEventSnapshot> criticalFragmentationEventSnapshots) {
        this.criticalFragmentationEventSnapshots = criticalFragmentationEventSnapshots;
    }

    public SimulationInstanceSummaryStatistics getStatistics() {
        return statistics;
    }

    public int getStartLoad() {
        return startLoad;
    }

    public void setStartLoad(int startLoad) {
        this.startLoad = startLoad;
    }

    public int getLoadStep() {
        return loadStep;
    }

    public void setLoadStep(int loadStep) {
        this.loadStep = loadStep;
    }

    public void setStatistics(SimulationInstanceSummaryStatistics statistics) {
        this.statistics = statistics;
    }

    public int getCycleNum() {
        return cycleNum;
    }

    public void setCycleNum(int cycleNum) {
        this.cycleNum = cycleNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<CriticalBPEventSnapshot> getCriticalBPEventSnapshots() {
        return criticalBPEventSnapshots;
    }

    public void setCriticalBPEventSnapshots(ArrayList<CriticalBPEventSnapshot> criticalBPEventSnapshots) {
        this.criticalBPEventSnapshots = criticalBPEventSnapshots;
    }

    public ArrayList<SimulationInstanceSummary> getSimulationInstanceSummaries() {
        return simulationInstanceSummaries;
    }

    public void setSimulationInstanceSummaries(ArrayList<SimulationInstanceSummary> simulationInstanceSummaries) {
        this.simulationInstanceSummaries = simulationInstanceSummaries;
    }
}
