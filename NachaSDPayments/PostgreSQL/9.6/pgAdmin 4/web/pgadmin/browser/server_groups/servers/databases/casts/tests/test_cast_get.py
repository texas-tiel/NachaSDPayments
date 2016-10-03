# #################################################################
#
# pgAdmin 4 - PostgreSQL Tools
#
# Copyright (C) 2013 - 2016, The pgAdmin Development Team
# This software is released under the PostgreSQL Licence
#
# ##################################################################

from pgadmin.utils.route import BaseTestGenerator
from regression import test_utils as utils
from regression.test_utils import get_ids
from . import utils as cast_utils
from pgadmin.browser.server_groups.servers.tests import utils as server_utils
from pgadmin.browser.server_groups.servers.databases.tests import \
    utils as database_utils


class CastsGetTestCase(BaseTestGenerator):
    """ This class will fetch the cast node added under database node. """

    scenarios = [
        # Fetching default URL for cast node.
        ('Check Cast Node', dict(url='/browser/cast/obj/'))
    ]

    @classmethod
    def setUpClass(cls):
        """
        This function used to add the sever, database, and cast

        :return: None
        """

        # Add the server
        server_utils.add_server(cls.tester)

        # Connect to server
        cls.server_connect_response, cls.server_group, cls.server_ids = \
            server_utils.connect_server(cls.tester)

        if len(cls.server_connect_response) == 0:
            raise Exception("No Server(s) connected to add the database!!!")

        # Add database
        database_utils.add_database(cls.tester, cls.server_connect_response,
                                    cls.server_ids)

        cast_utils.add_cast(cls.tester)

    def runTest(self):
        """ This function will get added cast."""

        all_id = get_ids()
        server_ids = all_id["sid"]
        db_ids_dict = all_id["did"][0]
        cast_ids_dict = all_id["cid"][0]

        for server_id in server_ids:
            db_id = db_ids_dict[int(server_id)]
            db_con = database_utils.verify_database(self.tester,
                                                    utils.SERVER_GROUP,
                                                    server_id,
                                                    db_id)
            if len(db_con) == 0:
                raise Exception("No database(s) to delete for server id %s"
                                % server_id)
            cast_id = cast_ids_dict[server_id]
            cast_get_data = cast_utils.verify_cast(self.tester,
                                                   utils.SERVER_GROUP,
                                                   server_id,
                                                   db_id, cast_id)
            self.assertEquals(cast_get_data.status_code, 200)

    @classmethod
    def tearDownClass(cls):
        """
        This function deletes the added cast, database, server and the
        'parent_id.pkl' file which is created in setup() function.

        :return: None
        """

        cast_utils.delete_cast(cls.tester)
        database_utils.delete_database(cls.tester)
        server_utils.delete_server(cls.tester)
        utils.delete_parent_id_file()
