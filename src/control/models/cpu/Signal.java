package control.models.cpu;

public class Signal {
    public byte data;

    public Signal() {}

    public Signal(int data) {
        this.data = (byte)data;
    }


    public boolean isOn () {
        if (this.data == 1)
            return true;
        else
            return false;
    }
}