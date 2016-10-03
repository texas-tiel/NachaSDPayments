define(
        ['jquery', 'underscore', 'underscore.string', 'pgadmin', 'pgadmin.browser', 'alertify', 'pgadmin.browser.collection'],
function($, _, S, pgAdmin, pgBrowser, alertify) {

  if (!pgBrowser.Nodes['coll-synonym']) {
    var databases = pgAdmin.Browser.Nodes['coll-synonym'] =
      pgAdmin.Browser.Collection.extend({
        node: 'synonym',
        label: '{{ _('Synonyms') }}',
        type: 'coll-synonym',
        columns: ['name', 'owner','is_public_synonym']
      });
  };

  if (!pgBrowser.Nodes['synonym']) {
    pgAdmin.Browser.Nodes['synonym'] = pgBrowser.Node.extend({
      type: 'synonym',
      dialogHelp: '{{ url_for('help.static', filename='synonym_dialog.html') }}',
      label: '{{ _('Synonym') }}',
      collection_type: 'coll-synonym',
      hasSQL: true,
      hasDepends: true,
      parent_type: ['schema', 'catalog'],
      Init: function() {
        /* Avoid mulitple registration of menus */
        if (this.initialized)
          return;

        this.initialized = true;

        pgBrowser.add_menus([{
          name: 'create_synonym_on_coll', node: 'coll-synonym', module: this,
          applies: ['object', 'context'], callback: 'show_obj_properties',
          category: 'create', priority: 4, label: '{{ _('Synonym...') }}',
          icon: 'wcTabIcon icon-synonym', data: {action: 'create', check: true},
          enable: 'canCreate'
        },{
          name: 'create_synonym', node: 'synonym', module: this,
          applies: ['object', 'context'], callback: 'show_obj_properties',
          category: 'create', priority: 4, label: '{{ _('Synonym...') }}',
          icon: 'wcTabIcon icon-synonym', data: {action: 'create', check: true},
          enable: 'canCreate'
        },{
          name: 'create_synonym', node: 'schema', module: this,
          applies: ['object', 'context'], callback: 'show_obj_properties',
          category: 'create', priority: 4, label: '{{ _('Synonym...') }}',
          icon: 'wcTabIcon icon-synonym', data: {action: 'create', check: true},
          enable: 'canCreate'
        }
        ]);

      },
      canDrop: pgBrowser.Nodes['schema'].canChildDrop,
      model: pgAdmin.Browser.Node.Model.extend({
        isNew: function() {
          return !this.fetchFromServer;
        },
        idAttribute: 'name',
        // Default values!
        initialize: function(attrs, args) {
          var isNew = (_.size(attrs) === 0);

          if (isNew) {
            var userInfo = pgBrowser.serverInfo[args.node_info.server._id].user;
            var schemaInfo = args.node_info.schema;
            this.set({
                'owner': userInfo.name,
                'synobjschema': schemaInfo.label,
                'schema': schemaInfo.label,
                'targettype': 'r'
            }, {silent: true});
          } else {
            this.fetchFromServer = true;
          }
          pgAdmin.Browser.Node.Model.prototype.initialize.apply(this, arguments);

        },
        schema: [{
          id: 'name', label: '{{ _('Name') }}', cell: 'string',
          type: 'text', mode: ['properties', 'create', 'edit'],
          disabled: 'inSchemaWithModelCheck'
        },{
          id: 'owner', label:'{{ _('Owner') }}', cell: 'string',
          type: 'text', mode: ['properties', 'create', 'edit'],
          disabled: true , control: 'node-list-by-name',
          node: 'role'
        },{
          id: 'schema', label:'{{ _('Schema') }}', cell: 'string',
          type: 'text', mode: ['properties', 'create', 'edit'],
          disabled: true , control: 'node-list-by-name',
          node: 'schema'
        },{
          type: 'nested', control: 'fieldset', label: '{{ _('Definition') }}',
          schema:[{
              id: 'targettype', label:'{{ _('Target Type') }}', cell: 'string',
              disabled: 'inSchema', group: '{{ _('Definition') }}',
              select2: { width: "50%", allowClear: false },
              options: function(obj) {
                  return [
                    {label: "Table", value: "r"},
                    {label: "Sequence", value: "S"},
                    {label: "View", value: "v"},
                    {label: "Function", value: "f"},
                    {label: "Procedure", value: "p"},
                    {label: "Public Synonym", value: "s"}
                  ]
               },
              control: 'select2'
            },{
              id: 'synobjschema', label:'{{ _('Target Schema') }}', cell: 'string',
              type: 'text', mode: ['properties', 'create', 'edit'],
              group: '{{ _('Definition') }}', deps: ['targettype'],
              select2: { allowClear: false }, control: 'node-list-by-name',
              node: 'schema', filter: function(d) {
                // Exclude PPAS catalogs
                var exclude_catalogs = ['pg_catalog', 'sys', 'dbo',
                                'pgagent', 'information_schema',
                                'dbms_job_procedure'];
                return d && _.indexOf(exclude_catalogs, d.label) == -1;
              },
              disabled: function(m) {
                // If tagetType is synonym then disable it
                if(!m.inSchema.apply(this, [m])) {
                  var is_synonym = (m.get('targettype') == 's');
                  if(is_synonym) {
                      m.set('synobjschema', 'public', {silent: true});
                    return true;
                  } else {
                    return false;
                  }
                }
                return true;
              }
            },{
              id: 'synobjname', label:'{{ _('Target Object') }}', cell: 'string',
              type: 'text', disabled: 'inSchema', group: '{{ _('Definition') }}',
              deps: ['targettype', 'synobjschema'],
                control: 'node-ajax-options',
                options: function(control) {
                  var trgTyp = control.model.get('targettype');
                  var trgSchema = control.model.get('synobjschema');
                  var res = [];

                  var node = control.field.get('schema_node'),
                  _url = node.generate_url.apply(
                    node, [
                    null, 'get_target_objects', control.field.get('node_data'), false,
                    control.field.get('node_info') ]);
                  $.ajax({
                      type: 'GET',
                      timeout: 30000,
                      url: _url,
                      cache: false,
                      async: false,
                      data: {"trgTyp" : trgTyp, "trgSchema" : trgSchema},

                      // On success return function list from server
                      success: function(result) {
                        res = result.data;
                        return res;
                      },

                      // On failure show error appropriate error message to user
                      error: function(xhr, status, error) {
                        try {
                          var err = $.parseJSON(xhr.responseText);
                          if (err.success == 0) {
                            alertify.error(err.errormsg);
                          }
                        } catch (e) {}
                      }
                  });
                return res;
              }
            }]
        },{
          id: 'is_public_synonym', label:'{{ _('Public Synonym?') }}',
          disabled: true, type: 'switch', mode: ['properties'], cell: 'switch',
          options: { onText: 'Yes', offText: 'No', onColor: 'success',
                    offColor: 'primary', size: 'mini'}
        }
        ],
        validate: function() {
          var err = {},
          msg = undefined;
          this.errorModel.clear();

          if (_.isUndefined(this.get('name'))
              || String(this.get('name')).replace(/^\s+|\s+$/g, '') == '') {
            msg = '{{ _('Name cannot be empty.') }}';
            this.errorModel.set('name', msg);
          } else if (_.isUndefined(this.get('synobjschema'))
              || String(this.get('synobjschema')).replace(/^\s+|\s+$/g, '') == '') {
            msg = '{{ _('Target schema cannot be empty.') }}';
            this.errorModel.set('synobjschema', msg);
          } else if (_.isUndefined(this.get('synobjname'))
              || String(this.get('synobjname')).replace(/^\s+|\s+$/g, '') == '') {
            msg = '{{ _('Target object cannot be empty.') }}';
            this.errorModel.set('synobjname', msg);
          }
          return null;
        },
        // We will disable everything if we are under catalog node
        inSchema: function() {
          if(this.node_info &&  'catalog' in this.node_info)
          {
            return true;
          }
          return false;
        },
        // We will check if we are under schema node & in 'create' mode
        inSchemaWithModelCheck: function(m) {
          if(this.node_info &&  'schema' in this.node_info)
          {
            // We will disbale control if it's in 'edit' mode
            if (m.isNew()) {
              return false;
            } else {
              return true;
            }
          }
          return true;
        }
      }),
      canCreate: function(itemData, item, data) {
          //If check is false then , we will allow create menu
          if (data && data.check == false)
            return true;

          var treeData = this.getTreeNodeHierarchy(item),
                server = treeData['server'];

          if (server && server.server_type === 'pg')
            return false;

          // If it is catalog then don't allow user to create synonyms
          if (treeData['catalog'] != undefined)
            return false;

          // by default we do not want to allow create menu
          return true;
      }
  });

  }

  return pgBrowser.Nodes['synonym'];
});
