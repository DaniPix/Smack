package net.processone.sm.packet;

import org.jivesoftware.smack.packet.IQ;

public class Push extends IQ {
    public static enum Send {all, firstPerUser, first, none}

    public static enum From {jid, username, name, none}

    public boolean sandbox;
    public int keepalive;
    public int session;
    public Send send;
    public boolean groupchat;
    public From from;
    public String status;
    public String statusMsg;
    public boolean offline;
    public String deviceType;
    public String deviceId;
    public String appId;

    public Push(int keepalive, int session) {
        this(false, keepalive, session, Send.all, false, From.none, null, null, false, null, null, null);
    }

    public Push(boolean sandbox, int keepalive, int session, Send send, boolean groupchat, From from, String status,
                String statusMsg, boolean offline, String deviceType, String deviceId, String appId) {
        super("push", "p1:push");
        this.sandbox = sandbox;
        this.keepalive = keepalive;
        this.session = session;
        this.send = send;
        this.groupchat = groupchat;
        this.from = from;
        this.status = status;
        this.statusMsg = statusMsg;
        this.offline = offline;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.appId = appId;
    }

    @Override
    protected IQChildElementXmlStringBuilder getIQChildElementBuilder(IQChildElementXmlStringBuilder xml) {
        xml.attribute("apns-sandbox", sandbox);
        xml.rightAngleBracket();
        xml.halfOpenElement("keepalive");
        xml.attribute("max", this.keepalive);
        xml.closeEmptyElement();
        xml.halfOpenElement("session");
        xml.attribute("duration", this.session);
        xml.closeEmptyElement();
        if (this.deviceId != null) {
            xml.halfOpenElement("body");
            xml.attribute("send", this.send);
            xml.attribute("groupchat", this.groupchat);
            xml.attribute("from", this.from);
            xml.closeEmptyElement();
            if (this.status != null) {
                xml.halfOpenElement("status");
                xml.attribute("type", this.status);
                xml.escape(this.statusMsg);
                xml.closeElement("status");
            }
            xml.openElement("notification");
            xml.element("type", this.deviceType);
            xml.element("id", this.deviceId);
            xml.closeElement("notification");
            xml.element("appid", this.appId);
        }
        return xml;
    }
}
