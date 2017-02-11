package com.sohail.events;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Registration  {



        private Columns columns;
        private List<Row> rows = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Columns getColumns() {
            return columns;
        }

        public void setColumns(Columns columns) {
            this.columns = columns;
        }

        public List<Row> getRows() {
            return rows;
        }

        public void setRows(List<Row> rows) {
            this.rows = rows;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }


}

