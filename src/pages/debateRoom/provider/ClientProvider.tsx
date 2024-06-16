import React, { createContext, useContext, useEffect, useMemo } from 'react';
import Client from '../store/Client';
import User from '../../../models/User';
import { DebateRoomWithUserInformation } from '../../../models/DebateRoom';

const ClientContext = createContext<Client | undefined>(undefined);

export const ClientProvider = ({
  debateRoomId,
  user,
  debateRoom,
  children,
}: {
  debateRoom: DebateRoomWithUserInformation | null;
  user: User | null;
  debateRoomId: number;
  children: React.ReactNode;
}) => {
  const client = useMemo(
    () => new Client(debateRoomId, user, debateRoom),
    [debateRoom, debateRoomId, user],
  );

  useEffect(() => {
    if (client.isLoggedIn) {
      client.connect();
    }
    return () => {
      client.exit();
    };
  }, [client]);

  return (
    <ClientContext.Provider value={client}>{children}</ClientContext.Provider>
  );
};

export const useClient = (): Client => {
  const context = useContext(ClientContext);
  if (context === null) {
    throw new Error('useClient must be used within a ClientProvider');
  }
  return context!;
};
