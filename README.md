StackButton
===========

A android widget to show stacked item when clicked.

![](./slide2.gif)


How to use
----------

You can integrate this into your exists project easily. You only need to register StackButton when your activity started.

     mStackButtonLayer = new StackButton(this, R.layout.stack_button);
     mStackButtonLayer.show();

And there must be one element in `R.layout.stack_button` with a id `@id/stack`. If you want to replace badge image, you just need to replace directly in this layout.

How to receive click event
-----------

Set `onClickListener` like normal view.

     private void setStackButtonClickListener(StackButton stackButtonLayer) {
         stackButtonLayer.setItemClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 makeIdToast(view.getId());
              }

              private void makeIdToast(int id) {
                  String resourceName = getResources().getResourceName(id);
                  Toast.makeText(MainActivity.this, resourceName, Toast.LENGTH_SHORT).show();
              }
          });
      }

