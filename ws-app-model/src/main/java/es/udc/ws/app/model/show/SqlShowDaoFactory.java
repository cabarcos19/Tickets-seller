package es.udc.ws.app.model.show;

import es.udc.ws.util.configuration.ConfigurationParametersManager;
public class SqlShowDaoFactory {

    private final static String CLASS_NAME_PARAMETER = "SqlShowDaoFactory.className";
    private static SqlShowDao dao = null;

    private SqlShowDaoFactory() {
    }

    @SuppressWarnings("rawtypes")
    private static SqlShowDao getInstance() {
        try {
            String daoClassName = ConfigurationParametersManager
                    .getParameter(CLASS_NAME_PARAMETER);
            Class daoClass = Class.forName(daoClassName);
            return (SqlShowDao) daoClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized static SqlShowDao getDao() {

        if (dao == null) {
            dao = getInstance();
        }
        return dao;

    }
}
