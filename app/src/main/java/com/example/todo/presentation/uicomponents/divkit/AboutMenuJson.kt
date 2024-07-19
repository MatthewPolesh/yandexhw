val card = """{
"card": {
    "variables": [
        {
        "name": "bgColor",
        "type": "color",
        "value": "#000ccc" 
        },
        {
            "name": "secondary",
            "type": "color",
            "value": "#c4c4c4"
        },
        {
            "name": "onSecondary",
            "type": "color",
            "value": "#000"
        },
        {
            "name": "onPrimary",
            "type": "color",
            "value": "#00ffff"
        },
        {
            "name": "blue",
            "type": "color",
            "value": "#fff000"
        },
        {
            "name": "font",
            "type": "string",
            "value": "#000"
        },
        {
            "name": "about_app",
            "type": "string",
            "value": "О приложении"
        },
        {
            "name": "text_app",
            "type": "string",
            "value": "Много текста..."
        },
        {
            "name": "developer",
            "type": "string",
            "value": "Разработчик"
        },
        {
            "name": "dev_name",
            "type": "string",
            "value": "Полещук М.С."
        }
    ],
  "log_id": "about_screen",
  "states": [
    {
      "state_id": 1,
      "div": {
        "type": "container",
        "width":{"type": "match_parent"},
        "height":{"type": "match_parent"},
        "background": [{
            "type": "solid",
            "color": "@{bgColor}"
        }],
        "orientation": "vertical",
        "paddings": {"top": 10, "start": 10, "end": 10, "bottom": 10 },
        "items": [
          {
            "type": "text",
            "text": "@{about_app}",
            "font_size": 32,
            "text_color": "@{onPrimary}",
            "font_weight": "bold",
            "font_family": "display",
            "margins": {"bottom": 10, "start": 30, "top": 30}
          },
          {
            "type": "container",
            "orientation": "vertical",
            "paddings": {"top": 10, "bottom": 10,"start": 10,"end": 10},
            "background": [
            {
                "type": "solid",
                "color": "@{secondary}"

            }
        ],
        "border": {"corner_radius": 20},
        "items":[
            {
            "type": "text",
            "text": "@{text_app}",
            "text_color": "@{onSecondary}",
            "font_size": 16,
            "font_weight": "regular",
            "font_family": "display"
          },
          {
            "type": "separator",
            "paddings": {"top": 10, "bottom": 10}
          },
          {
            "type": "text",
            "text": "@{developer}",
            "text_color": "@{onSecondary}",
            "font_size": 20,
            "font_weight": "regular",
            "font_family": "display"
          },
          {
            "type": "container",
            "orientation": "horizontal",
            "items": [
                {
            "type": "image",
            "image_url": "https://cdn-icons-png.flaticon.com/512/10169/10169718.png",
            "scale":"fit",
            "height":{"type": "fixed", "value": 24,"unit": "dp"},
            "width":{"type": "wrap_content"},
            "alignment_horizontal": "start"
            },
            {
                "type": "text",
                "text": "@{dev_name}",
                "font_weight": "regular",
                "font_family": "display",
                "text_color": "@{onSecondary}",
                "font_size": 14,
                "alignment_vertical": "center",
                "margins": {"start": 5}
            }
            ]
          }
        ]
          },
          
           {
            "type": "text",
            "text": "OK",
            "actions": [{
            "log_id": "buttonPressed",
            "url": "div-action://navigate"
             }],
            "width": { "type":"wrap_content"},
            "font_weight": "light",
            "font_family": "display",
            "font_size": 14,
            "text_color": "@{blue}",
            "margins": {"top": 20},
            "paddings": {"top": 5, "bottom": 5, "end": 5, "start": 5},
            "alignment_horizontal": "center"
          }
          
        ]
      }
    }
  ]
}
}
""".trimIndent()