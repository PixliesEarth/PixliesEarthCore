package eu.pixliesearth.core.custom.machines;

import eu.pixliesearth.core.custom.CustomFuelableCrafterMachine;
import eu.pixliesearth.core.custom.MinecraftMaterial;

public class MachineDistillery extends CustomFuelableCrafterMachine {

    @Override
    public String getFuelUUID() {
        return MinecraftMaterial.WATER_BUCKET.getUUID();
    }

    @Override
    public String getPlayerHeadUUID() {
        return "48437fee749cbd68f3443bb06e45058a687c0fca96436cadb538cad59b5216c6";
    }

    @Override
    public String getDefaultDisplayName() {
        return "§bDistillery";
    }

    @Override
    public String getUUID() {
        return "Machine:Distillery"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

}
