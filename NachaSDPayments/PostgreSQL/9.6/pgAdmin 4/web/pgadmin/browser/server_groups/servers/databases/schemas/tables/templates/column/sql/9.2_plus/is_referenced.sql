SELECT COUNT(1)
FROM pg_depend dep
    JOIN pg_class cl ON dep.classid=cl.oid AND relname='pg_rewrite'
    WHERE refobjid= {{tid}}::oid
    AND refobjsubid= {{clid|qtLiteral}};
