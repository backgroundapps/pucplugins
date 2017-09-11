package com.tecgraf.plugins;

import com.tecgraf.plugins.client.Client;
import org.junit.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static org.junit.Assert.assertNotNull;

public class ClientTest {
    @Test
    public void shouldReturnSomething() throws RemoteException, NotBoundException {
        assertNotNull(Client.getServer().getUsers());
    }
}
