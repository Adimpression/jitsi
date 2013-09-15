/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.service.protocol;

import net.java.sip.communicator.service.protocol.event.*;
import java.util.*;

/**
 * An abstract class with a default implementation of some of the methods of
 * the <tt>ChatRoom</tt> interface.
 *
 * @author Boris Grozev
 */
public abstract class AbstractChatRoom
    implements ChatRoom
{
    /**
     * The list of listeners to be notified when a member of the chat room
     * publishes a <tt>ConferenceDescription</tt>
     */
    protected final List<ChatRoomConferencePublishedListener>
            conferencePublishedListeners
                = new LinkedList<ChatRoomConferencePublishedListener>();

    /**
     * {@inheritDoc}
     */
    public void addConferencePublishedListener(
            ChatRoomConferencePublishedListener listener)
    {
        synchronized (conferencePublishedListeners)
        {
            conferencePublishedListeners.add(listener);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeConferencePublishedListener(
            ChatRoomConferencePublishedListener listener)
    {
        synchronized (conferencePublishedListeners)
        {
            conferencePublishedListeners.remove(listener);
        }
    }

    /**
     * Creates the corresponding <tt>ChatRoomConferencePublishedEvent</tt> and
     * notifies all <tt>ChatRoomConferencePublishedListener</tt>s that
     * <tt>member</tt> has published a conference description.
     *
     * @param member the <tt>ChatRoomMember</tt> that published <tt>cd</tt>.
     * @param cd the <tt>ConferenceDescription</tt> that was published.
     */
    protected void fireConferencePublishedEvent(
            ChatRoomMember member,
            ConferenceDescription cd)
    {
        ChatRoomConferencePublishedEvent evt
                = new ChatRoomConferencePublishedEvent(this, member, cd);

        List<ChatRoomConferencePublishedListener> listeners;
        synchronized (conferencePublishedListeners)
        {
            listeners  = new LinkedList<ChatRoomConferencePublishedListener>(
                    conferencePublishedListeners);
        }

        for (ChatRoomConferencePublishedListener listener : listeners)
            listener.conferencePublished(evt);
    }
}
