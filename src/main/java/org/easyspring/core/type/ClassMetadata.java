package org.easyspring.core.type;

public interface ClassMetadata {

     boolean isAbstract();

     boolean isInterface();

     boolean isFinal();

     String getClassName();

     String getSuperClassName();

     String[] getInterfaceNames();
}
