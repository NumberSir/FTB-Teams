package dev.ftb.mods.ftbteams.api;

import net.minecraft.network.chat.Component;

import java.util.UUID;

/// Represents a message sent via the team chat. Could be from a player, or a system message.
///
/// You can create an instance of this via [dev.ftb.mods.ftbteams.api.FTBTeamsAPI.API#createMessage(UUID, Component)];
/// the timestamp will be automatically set to the value of [System#currentTimeMillis()].
///
public interface TeamMessage {
    /// The sender's ID. System messages use a sender ID of [net.minecraft.util.Util#NIL_UUID].
    ///
    /// @return the sender's ID
    UUID sender();

    /// Message timestamp, in milliseconds since epoch, i.e. the result of [System#currentTimeMillis()].
    ///
    /// @return the message timestamp
    long date();

    /// The message text.
    ///
    /// @return message text
    Component text();
}
