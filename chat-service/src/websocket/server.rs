//! Orig from https://github.com/actix/examples/tree/master/websockets/chat-actorless, improved by Ming Chang.
//!
//!                                 Apache License
//!                           Version 2.0, January 2004
//!                        http://www.apache.org/licenses/
//!
//!   TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION
//!
//!   1. Definitions.
//!
//!      "License" shall mean the terms and conditions for use, reproduction,
//!      and distribution as defined by Sections 1 through 9 of this document.
//!
//!      "Licensor" shall mean the copyright owner or entity authorized by
//!      the copyright owner that is granting the License.
//!
//!      "Legal Entity" shall mean the union of the acting entity and all
//!      other entities that control, are controlled by, or are under common
//!      control with that entity. For the purposes of this definition,
//!      "control" means (i) the power, direct or indirect, to cause the
//!      direction or management of such entity, whether by contract or
//!      otherwise, or (ii) ownership of fifty percent (50%) or more of the
//!      outstanding shares, or (iii) beneficial ownership of such entity.
//!
//!      "You" (or "Your") shall mean an individual or Legal Entity
//!      exercising permissions granted by this License.
//!
//!      "Source" form shall mean the preferred form for making modifications,
//!      including but not limited to software source code, documentation
//!      source, and configuration files.
//!
//!      "Object" form shall mean any form resulting from mechanical
//!      transformation or translation of a Source form, including but
//!      not limited to compiled object code, generated documentation,
//!      and conversions to other media types.
//!
//!      "Work" shall mean the work of authorship, whether in Source or
//!      Object form, made available under the License, as indicated by a
//!      copyright notice that is included in or attached to the work
//!      (an example is provided in the Appendix below).
//!
//!      "Derivative Works" shall mean any work, whether in Source or Object
//!      form, that is based on (or derived from) the Work and for which the
//!      editorial revisions, annotations, elaborations, or other modifications
//!      represent, as a whole, an original work of authorship. For the purposes
//!      of this License, Derivative Works shall not include works that remain
//!      separable from, or merely link (or bind by name) to the interfaces of,
//!      the Work and Derivative Works thereof.
//!
//!      "Contribution" shall mean any work of authorship, including
//!      the original version of the Work and any modifications or additions
//!      to that Work or Derivative Works thereof, that is intentionally
//!      submitted to Licensor for inclusion in the Work by the copyright owner
//!      or by an individual or Legal Entity authorized to submit on behalf of
//!      the copyright owner. For the purposes of this definition, "submitted"
//!      means any form of electronic, verbal, or written communication sent
//!      to the Licensor or its representatives, including but not limited to
//!      communication on electronic mailing lists, source code control systems,
//!      and issue tracking systems that are managed by, or on behalf of, the
//!      Licensor for the purpose of discussing and improving the Work, but
//!      excluding communication that is conspicuously marked or otherwise
//!      designated in writing by the copyright owner as "Not a Contribution."
//!
//!      "Contributor" shall mean Licensor and any individual or Legal Entity
//!      on behalf of whom a Contribution has been received by Licensor and
//!      subsequently incorporated within the Work.
//!
//!   2. Grant of Copyright License. Subject to the terms and conditions of
//!      this License, each Contributor hereby grants to You a perpetual,
//!      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
//!      copyright license to reproduce, prepare Derivative Works of,
//!      publicly display, publicly perform, sublicense, and distribute the
//!      Work and such Derivative Works in Source or Object form.
//!
//!   3. Grant of Patent License. Subject to the terms and conditions of
//!      this License, each Contributor hereby grants to You a perpetual,
//!      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
//!      (except as stated in this section) patent license to make, have made,
//!      use, offer to sell, sell, import, and otherwise transfer the Work,
//!      where such license applies only to those patent claims licensable
//!      by such Contributor that are necessarily infringed by their
//!      Contribution(s) alone or by combination of their Contribution(s)
//!      with the Work to which such Contribution(s) was submitted. If You
//!      institute patent litigation against any entity (including a
//!      cross-claim or counterclaim in a lawsuit) alleging that the Work
//!      or a Contribution incorporated within the Work constitutes direct
//!      or contributory patent infringement, then any patent licenses
//!      granted to You under this License for that Work shall terminate
//!      as of the date such litigation is filed.
//!
//!   4. Redistribution. You may reproduce and distribute copies of the
//!      Work or Derivative Works thereof in any medium, with or without
//!      modifications, and in Source or Object form, provided that You
//!      meet the following conditions:
//!
//!      (a) You must give any other recipients of the Work or
//!          Derivative Works a copy of this License; and
//!
//!      (b) You must cause any modified files to carry prominent notices
//!          stating that You changed the files; and
//!
//!       (c) You must retain, in the Source form of any Derivative Works
//!           that You distribute, all copyright, patent, trademark, and
//!           attribution notices from the Source form of the Work,
//!           excluding those notices that do not pertain to any part of
//!           the Derivative Works; and
//!
//!       (d) If the Work includes a "NOTICE" text file as part of its
//!           distribution, then any Derivative Works that You distribute must
//!           include a readable copy of the attribution notices contained
//!           within such NOTICE file, excluding those notices that do not
//!           pertain to any part of the Derivative Works, in at least one
//!           of the following places: within a NOTICE text file distributed
//!           as part of the Derivative Works; within the Source form or
//!           documentation, if provided along with the Derivative Works; or,
//!           within a display generated by the Derivative Works, if and
//!           wherever such third-party notices normally appear. The contents
//!           of the NOTICE file are for informational purposes only and
//!           do not modify the License. You may add Your own attribution
//!           notices within Derivative Works that You distribute, alongside
//!           or as an addendum to the NOTICE text from the Work, provided
//!           that such additional attribution notices cannot be construed
//!           as modifying the License.
//!
//!       You may add Your own copyright statement to Your modifications and
//!       may provide additional or different license terms and conditions
//!       for use, reproduction, or distribution of Your modifications, or
//!       for any such Derivative Works as a whole, provided Your use,
//!       reproduction, and distribution of the Work otherwise complies with
//!       the conditions stated in this License.
//!
//!    5. Submission of Contributions. Unless You explicitly state otherwise,
//!       any Contribution intentionally submitted for inclusion in the Work
//!       by You to the Licensor shall be under the terms and conditions of
//!       this License, without any additional terms or conditions.
//!       Notwithstanding the above, nothing herein shall supersede or modify
//!       the terms of any separate license agreement you may have executed
//!       with Licensor regarding such Contributions.
//!
//!    6. Trademarks. This License does not grant permission to use the trade
//!       names, trademarks, service marks, or product names of the Licensor,
//!       except as required for reasonable and customary use in describing the
//!       origin of the Work and reproducing the content of the NOTICE file.
//!
//!    7. Disclaimer of Warranty. Unless required by applicable law or
//!       agreed to in writing, Licensor provides the Work (and each
//!       Contributor provides its Contributions) on an "AS IS" BASIS,
//!       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
//!       implied, including, without limitation, any warranties or conditions
//!       of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A
//!       PARTICULAR PURPOSE. You are solely responsible for determining the
//!       appropriateness of using or redistributing the Work and assume any
//!       risks associated with Your exercise of permissions under this License.
//!
//!    8. Limitation of Liability. In no event and under no legal theory,
//!       whether in tort (including negligence), contract, or otherwise,
//!       unless required by applicable law (such as deliberate and grossly
//!       negligent acts) or agreed to in writing, shall any Contributor be
//!       liable to You for damages, including any direct, indirect, special,
//!       incidental, or consequential damages of any character arising as a
//!       result of this License or out of the use or inability to use the
//!       Work (including but not limited to damages for loss of goodwill,
//!       work stoppage, computer failure or malfunction, or any and all
//!       other commercial damages or losses), even if such Contributor
//!       has been advised of the possibility of such damages.
//!
//!    9. Accepting Warranty or Additional Liability. While redistributing
//!       the Work or Derivative Works thereof, You may choose to offer,
//!       and charge a fee for, acceptance of support, warranty, indemnity,
//!       or other liability obligations and/or rights consistent with this
//!       License. However, in accepting such obligations, You may act only
//!       on Your own behalf and on Your sole responsibility, not on behalf
//!       of any other Contributor, and only if You agree to indemnify,
//!       defend, and hold each Contributor harmless for any liability
//!       incurred by, or claims asserted against, such Contributor by reason
//!       of your accepting any such warranty or additional liability.
//!
//!    END OF TERMS AND CONDITIONS
//!
//!    APPENDIX: How to apply the Apache License to your work.
//!
//!       To apply the Apache License to your work, attach the following
//!       boilerplate notice, with the fields enclosed by brackets "[]"
//!       replaced with your own identifying information. (Don't include
//!       the brackets!)  The text should be enclosed in the appropriate
//!       comment syntax for the file format. We also recommend that a
//!       file or class name and description of purpose be included on the
//!       same "printed page" as the copyright notice for easier
//!       identification within third-party archives.
//!
//!    Copyright [yyyy] [name of copyright owner]
//!
//!    Licensed under the Apache License, Version 2.0 (the "License");
//!    you may not use this file except in compliance with the License.
//!    You may obtain a copy of the License at
//!
//!        http://www.apache.org/licenses/LICENSE-2.0
//!
//!    Unless required by applicable law or agreed to in writing, software
//!    distributed under the License is distributed on an "AS IS" BASIS,
//!    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//!    See the License for the specific language governing permissions and
//!    limitations under the License.

//! A multi-room chat server.

use std::{
    collections::{HashMap, HashSet},
    io,
};

use rand::{thread_rng, Rng as _};
use tokio::sync::{mpsc, oneshot};

use crate::router::model::AddChatMessageRequest;
use crate::service::chat_history::ChatHistory;
use crate::{ConnId, Msg, PgPool, RoomId, UserId};

/// A command received by the [`ChatServer`].
#[derive(Debug)]
enum Command {
    Connect {
        conn_tx: mpsc::UnboundedSender<Msg>,
        res_tx: oneshot::Sender<ConnId>,
        room: RoomId,
        user: UserId,
    },

    Disconnect {
        conn: ConnId,
        user: UserId,
    },

    Message {
        msg: Msg,
        conn: ConnId,
        user: UserId,
        res_tx: oneshot::Sender<()>,
    },
}

/// A multi-room chat server.
///
/// Contains the logic of how connections chat with each other plus room management.
///
/// Call and spawn [`run`](Self::run) to start processing commands.
#[derive(Debug)]
pub struct ChatServer {
    /// Map of connection IDs to their message receivers.
    sessions: HashMap<(ConnId, UserId), mpsc::UnboundedSender<Msg>>,

    /// Map of room name to participant IDs in that room.
    rooms: HashMap<RoomId, HashSet<(ConnId, UserId)>>,

    /// Command receiver.
    cmd_rx: mpsc::UnboundedReceiver<Command>,
}

impl ChatServer {
    pub fn new() -> (Self, ChatServerHandle) {
        // create empty server
        let mut rooms = HashMap::with_capacity(4);

        // create default room
        rooms.insert("main".to_owned(), HashSet::new());

        let (cmd_tx, cmd_rx) = mpsc::unbounded_channel();

        (
            Self {
                sessions: HashMap::new(),
                rooms,
                cmd_rx,
            },
            ChatServerHandle { cmd_tx },
        )
    }

    /// Send message to users in a room.
    ///
    /// `skip` is used to prevent messages triggered by a connection also being received by it.
    async fn send_system_message(&self, room: &str, skip: ConnId, msg: impl Into<String>) {
        if let Some(sessions) = self.rooms.get(room) {
            let msg = msg.into();

            for session in sessions {
                if session.0 != skip {
                    if let Some(tx) = self.sessions.get(session) {
                        // errors if client disconnected abruptly and hasn't been timed-out yet
                        let _ = tx.send(msg.clone());
                    }
                }
            }
        }
    }

    /// Send message to all other users in current room.
    ///
    /// `conn` is used to find current room and prevent messages sent by a connection also being
    /// received by it.
    async fn send_message(&self, conn: (ConnId, UserId), msg: impl Into<String>) {
        if let Some(room) = self
            .rooms
            .iter()
            .find_map(|(room, participants)| participants.contains(&conn).then_some(room))
        {
            self.send_system_message(room, conn.0, msg).await;
        };
    }

    /// Register new session and assign unique ID to this session
    async fn connect(
        &mut self,
        tx: mpsc::UnboundedSender<Msg>,
        room: RoomId,
        user: UserId,
    ) -> ConnId {
        // register session with random connection ID
        let id = thread_rng().gen::<usize>();
        self.sessions.insert((id, user.clone()), tx);

        // auto join session to main room
        self.rooms
            .entry(room)
            .or_insert_with(HashSet::new)
            .insert((id, user));

        // send id back
        id
    }

    /// Unregister connection from room map and broadcast disconnection message.
    async fn disconnect(&mut self, conn_id: ConnId, user: UserId) {
        println!("Someone disconnected");

        let mut rooms: Vec<String> = Vec::new();

        // remove sender
        if self.sessions.remove(&(conn_id, user.clone())).is_some() {
            // remove session from all rooms
            for (name, sessions) in &mut self.rooms {
                if sessions.remove(&(conn_id, user.clone())) {
                    rooms.push(name.to_owned());
                }
            }
        }
    }

    /// 這邊處理WebSocket傳進來的指令
    pub async fn run(mut self) -> io::Result<()> {
        while let Some(cmd) = self.cmd_rx.recv().await {
            match cmd {
                Command::Connect {
                    conn_tx,
                    res_tx,
                    room,
                    user,
                } => {
                    let conn_id = self.connect(conn_tx, room, user).await;
                    let _ = res_tx.send(conn_id);
                }

                Command::Disconnect { conn, user } => {
                    self.disconnect(conn, user).await;
                }

                Command::Message {
                    conn,
                    user,
                    msg,
                    res_tx,
                } => {
                    self.send_message((conn, user), msg).await;
                    let _ = res_tx.send(());
                }
            }
        }

        Ok(())
    }
}

/// Handle and command sender for chat server.
///
/// Reduces boilerplate of setting up response channels in WebSocket handlers.
#[derive(Debug, Clone)]
pub struct ChatServerHandle {
    cmd_tx: mpsc::UnboundedSender<Command>,
}

impl ChatServerHandle {
    /// Register client message sender and obtain connection ID.
    pub async fn connect(
        &self,
        conn_tx: mpsc::UnboundedSender<String>,
        room: RoomId,
        user: UserId,
    ) -> ConnId {
        let (res_tx, res_rx) = oneshot::channel();

        // unwrap: chat server should not have been dropped
        self.cmd_tx
            .send(Command::Connect {
                conn_tx,
                res_tx,
                room,
                user,
            })
            .unwrap();

        // unwrap: chat server does not drop out response channel
        res_rx.await.unwrap()
    }

    /// Broadcast message to current room.
    pub async fn send_message(
        &self,
        conn: ConnId,
        room: RoomId,
        user: UserId,
        msg: impl Into<String>,
        pool: &PgPool,
    ) {
        let (res_tx, res_rx) = oneshot::channel();

        let message = msg.into();

        // unwrap: chat server should not have been dropped
        self.cmd_tx
            .send(Command::Message {
                msg: message.clone(),
                conn,
                user: user.clone(),
                res_tx,
            })
            .unwrap();

        let request = AddChatMessageRequest {
            chat_room_id: room.parse().unwrap(),
            sent_user_id: user.parse().unwrap(),
            receive_user_id_list: vec![],
            message_content: message,
            sent_datetime: chrono::Local::now().naive_local(),
        };

        ChatHistory::save_chat_history(pool, request).await;

        // unwrap: chat server does not drop our response channel
        res_rx.await.unwrap();
    }

    /// Unregister message sender and broadcast disconnection message to current room.
    pub fn disconnect(&self, conn: ConnId, user: UserId) {
        // unwrap: chat server should not have been dropped
        self.cmd_tx
            .send(Command::Disconnect { conn, user })
            .unwrap();
    }
}
