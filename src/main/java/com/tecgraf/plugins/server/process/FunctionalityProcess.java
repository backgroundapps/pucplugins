package com.tecgraf.plugins.server.process;

import com.tecgraf.plugins.common.Functionality;
import com.tecgraf.plugins.server.dao.FunctionalityDAO;
import com.tecgraf.plugins.server.dao.utils.StatementBuilderFactory;
import com.tecgraf.plugins.server.dao.utils.StatementDDLBuilder;
import com.tecgraf.plugins.server.dao.utils.StatementDMLBuilder;
import com.tecgraf.plugins.server.process.utils.Mapper;

import java.sql.SQLException;
import java.util.List;

public class FunctionalityProcess {
    public List<Functionality> getFunctionalities() throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new FunctionalityDAO(ddl).listFunctionalities();

        }finally {
            ddl.close();
        }
    }

    public Long numberOfFunctionalities() throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new FunctionalityDAO(ddl).numberOfFunctionalities();

        }finally {
            ddl.close();
        }
    }

    public Functionality lastFunctionality() throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new FunctionalityDAO(ddl).lastFunctionality();

        }finally {
            ddl.close();
        }
    }

    public Long lastId() throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new FunctionalityDAO(ddl).lastId();

        }finally {
            ddl.close();
        }
    }

    public Functionality findById(Long id) throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new FunctionalityDAO(ddl).findById(id);

        }finally {
            ddl.close();
        }

    }

    public Functionality findByName(String name) throws SQLException{
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new FunctionalityDAO(ddl).findByName(name);

        }finally {
            ddl.close();
        }
    }


    public Long nextId() throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new FunctionalityDAO(ddl).nextId();

        }finally {
            ddl.close();
        }

    }

    public String[] getMappedNames() throws SQLException {
        List<Functionality> functionalities = getFunctionalities();
        String[] names = new String[functionalities.size()];

        names = Mapper.getFunctionalityNames(functionalities).toArray(names);

        return names;
    }

    public boolean create(Functionality functionality) throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        StatementDMLBuilder dml = StatementBuilderFactory.getDMLBuilderInstance();
        try {
            return new FunctionalityDAO(ddl, dml).create(functionality);

        }finally {
            ddl.close();
            dml.close();
        }
    }

    public boolean update(Functionality updatedFunctionality, Long id) throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        StatementDMLBuilder dml = StatementBuilderFactory.getDMLBuilderInstance();
        try {
            return new FunctionalityDAO(ddl, dml).update(updatedFunctionality ,id);

        }finally {
            ddl.close();
            dml.close();
        }
    }

    public boolean delete(Long id) throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        StatementDMLBuilder dml = StatementBuilderFactory.getDMLBuilderInstance();
        try {
            return new FunctionalityDAO(ddl, dml).delete(id);

        }finally {
            ddl.close();
            dml.close();
        }
    }

    public Object[][] getFullFunctionalityData(String functionalityName) throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new FunctionalityDAO(ddl).getFullFunctionalityData(functionalityName);

        }finally {
            ddl.close();
        }
    }

    public Object[][] getFullFunctionalityDataByUserId(Long userId) throws SQLException {
        StatementDDLBuilder ddl = StatementBuilderFactory.getDDLBuilderInstance();
        try {
            return new FunctionalityDAO(ddl).getFullFunctionalityDataByUserId(userId);

        }finally {
            ddl.close();
        }
    }
}
