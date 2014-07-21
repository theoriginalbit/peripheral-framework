@LuaPeripheral("monitor")
public class MonitorPeripheral {
    private final TileMonitor m_monitor;

    public MonitorPeripheral(TileMonitor monitor) {
        m_monitor = monitor;
    }

    @LuaFunction
    public void write(String string) {
        Terminal terminal = m_monitor.getTerminal().getTerminal();
        terminal.write(string);
        terminal.setCursorPos(terminal.getCursorX() + string.length(), terminal.getCursorY());
    }

    @LuaFunction
    public void scroll(int lines) {
        Terminal terminal = m_monitor.getTerminal().getTerminal();
        terminal.scroll(lines);
    }

    @LuaFunction
    public void setCursorPos(int x, int y) {
        Terminal terminal = m_monitor.getTerminal().getTerminal();
        terminal.setCursorPos(x-1, y-1);
    }

    @LuaFunction
    public void setCursorBlink(boolean blink) {
        Terminal terminal = m_monitor.getTerminal().getTerminal();
        terminal.setCursorBlink(blink);
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] getCursorPos() {
        Terminal terminal = m_monitor.getTerminal().getTerminal();
        return new Object[]{ terminal.getCursorX() + 1, terminal.getCursorY() + 1 };
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] getSize() {
        Terminal terminal = m_monitor.getTerminal().getTerminal();
        return new Object[]{ terminal.getWidth(), terminal.getHeight() };
    }

    @LuaFunction
    public void clear() {
        Terminal terminal = m_monitor.getTerminal().getTerminal();
        terminal.clear();
    }

    @LuaFunction
    public void clearLine() {
        Terminal terminal = m_monitor.getTerminal().getTerminal();
        terminal.clearLine();
    }

    @LuaFunction
    public void setTextScale(double scale) throws Exception {
        int s = (int) scale * 2.0d;
        if (s < 1 || s > 10) throw new Exception("Expected number in range 0.5-5");
        m_monitor.setTextScale(s);
    }

    @LuaFunction
    @Alias("setTextColor")
    public void setTextColour(int colour) {
        colour = TermAPI.parseColour(colour, m_monitor.getTerminal().isColour());
        Terminal terminal = m_monitor.getTerminal().getTerminal();
        terminal.setTextColour(colour);
    }

    @LuaFunction
    @Alias("setBackgroundColor")
    public void setBackgroundColour(int colour) {
        colour = TermAPI.parseColour(colour, m_monitor.getTerminal().isColour());
        Terminal terminal = m_monitor.getTerminal().getTerminal();
        terminal.setBackgroundColour(colour);
    }

    @Alias("isColor")
    @LuaFunction
    public boolean isColour() {
        return new m_monitor.getTerminal().isColour();
    }

    @Attach
    public void attach(IComputerAccess computer) {
        m_monitor.addComputer(computer);
    }

    @Detach
    public void detach(IComputerAccess computer) {
        m_monitor.removeComputer(computer);
    }
}