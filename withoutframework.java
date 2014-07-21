public class MonitorPeripheral implements IPeripheral {
    private final TileMonitor m_monitor;

    public MonitorPeripheral(TileMonitor monitor) {
        this.m_monitor = monitor;
    }

    public String getType() {
        return "monitor";
    }

    public String[] getMethodNames() {
        return new String[]{"write", "scroll", "setCursorPos", "setCursorBlink", "getCursorPos", "getSize", "clear", "clearLine", "setTextScale", "setTextColour", "setTextColor", "setBackgroundColour", "setBackgroundColor", "isColour", "isColor"};
    }

    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments)
            throws Exception {
        switch (method) {
            case 0:
                String string = null;
                if (arguments.length > 0) {
                    string = arguments[0].toString();
                } else {
                    string = "";
                }
                Terminal terminal = this.m_monitor.getTerminal().getTerminal();
                terminal.write(string);
                terminal.setCursorPos(terminal.getCursorX() + string.length(), terminal.getCursorY());
                return null;
            case 1:
                if ((arguments.length < 1) || (!(arguments[0] instanceof Number))) {
                    throw new Exception("Expected number");
                }
                Terminal terminal = this.m_monitor.getTerminal().getTerminal();
                terminal.scroll(((Number) arguments[0]).intValue());
                return null;
            case 2:
                if ((arguments.length < 2) || (!(arguments[0] instanceof Number)) || (!(arguments[1] instanceof Number))) {
                    throw new Exception("Expected number, number");
                }
                int x = ((Number) arguments[0]).intValue() - 1;
                int y = ((Number) arguments[1]).intValue() - 1;
                Terminal terminal = this.m_monitor.getTerminal().getTerminal();
                terminal.setCursorPos(x, y);
                return null;
            case 3:
                if ((arguments.length < 1) || (!(arguments[0] instanceof Boolean))) {
                    throw new Exception("Expected boolean");
                }
                Terminal terminal = this.m_monitor.getTerminal().getTerminal();
                terminal.setCursorBlink(((Boolean) arguments[0]).booleanValue());
                return null;
            case 4:
                Terminal terminal = this.m_monitor.getTerminal().getTerminal();
                return new Object[]{Integer.valueOf(terminal.getCursorX() + 1), Integer.valueOf(terminal.getCursorY() + 1)};
            case 5:
                Terminal terminal = this.m_monitor.getTerminal().getTerminal();
                return new Object[]{Integer.valueOf(terminal.getWidth()), Integer.valueOf(terminal.getHeight())};
            case 6:
                Terminal terminal = this.m_monitor.getTerminal().getTerminal();
                terminal.clear();
                return null;
            case 7:
                Terminal terminal = this.m_monitor.getTerminal().getTerminal();
                terminal.clearLine();
                return null;
            case 8:
                if ((arguments.length < 1) || (!(arguments[0] instanceof Number))) {
                    throw new Exception("Expected number");
                }
                int scale = (int) (((Number) arguments[0]).doubleValue() * 2.0D);
                if ((scale < 1) || (scale > 10)) {
                    throw new Exception("Expected number in range 0.5-5");
                }
                this.m_monitor.setTextScale(scale);
                return null;
            case 9:
            case 10:
                int colour = TermAPI.parseColour(arguments, this.m_monitor.getTerminal().isColour());
                Terminal terminal = this.m_monitor.getTerminal().getTerminal();
                terminal.setTextColour(colour);
                return null;
            case 11:
            case 12:
                int colour = TermAPI.parseColour(arguments, this.m_monitor.getTerminal().isColour());
                Terminal terminal = this.m_monitor.getTerminal().getTerminal();
                terminal.setBackgroundColour(colour);
                return null;
            case 13:
            case 14:
                return new Object[]{Boolean.valueOf(this.m_monitor.getTerminal().isColour())};
        }

        return null;
    }

    public void attach(IComputerAccess computer) {
        this.m_monitor.addComputer(computer);
    }

    public void detach(IComputerAccess computer) {
        this.m_monitor.removeComputer(computer);
    }

    public boolean equals(IPeripheral other) {
        if ((other != null) && ((other instanceof MonitorPeripheral))) {
            MonitorPeripheral otherMonitor = (MonitorPeripheral) other;
            if (otherMonitor.m_monitor == this.m_monitor) {
                return true;
            }
        }
        return false;
    }
}