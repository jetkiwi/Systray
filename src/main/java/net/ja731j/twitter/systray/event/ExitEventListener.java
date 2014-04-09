/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ja731j.twitter.systray.event;

import java.util.EventListener;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public interface ExitEventListener extends EventListener{
    public void onApplicationExit(ExitEvent e);
}
