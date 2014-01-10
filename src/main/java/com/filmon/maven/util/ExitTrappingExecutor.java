package com.filmon.maven.util;


import java.security.Permission;
import java.util.concurrent.Callable;

/**
 * Prevents plugin from being shut down by one of dependency libs.
 */
public class ExitTrappingExecutor {

    private SecurityManager securityManager = null;

    private void forbidSystemExitCall() {
        securityManager = System.getSecurityManager();

        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkExit(int status) {
                throw new ExitTrappedException(status);
            }

            @Override
            public void checkPermission(final Permission perm) {
                if (securityManager != null) {
                    securityManager.checkPermission(perm);
                }
            }
        });
    }

    private void revertSecurityManager() {
        System.setSecurityManager(securityManager);
    }


    public int execute(Callable callable) {
        forbidSystemExitCall();

        try {
            callable.call();
        } catch(ExitTrappedException e) {
            return e.getStatus();
        } catch (Exception e) {
            return -1;
        } finally {
            revertSecurityManager();
        }

        return 0;
    }

    public static class ExitTrappedException extends SecurityException {
        private final int status;

        public ExitTrappedException(final int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

}
