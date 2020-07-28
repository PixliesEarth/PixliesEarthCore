package eu.pixliesearth.core.machines;

import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
@AllArgsConstructor
public class MachineProcess {

    private List<ItemStack> result;
    private Timer timer;

}
