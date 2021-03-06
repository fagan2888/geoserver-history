.. _gwc_seeding:

Seeding and refreshing
======================

The primary benefit to GeoWebCache is that it allows for the acceleration of normal WMS tile request processing by eliminating the need for the tiles to be regenerated for every request.  This page discusses tile generation.

Generating tiles
----------------

There are two ways for tiles to be generated by GeoWebCache.  The first way for tiles to be generated is during **normal map viewing**.  In this case, tiles are cached only when they are requested from a client, either through map browsing (such as in OpenLayers) or through manual WMS tile requests.  The first time a map view is requested it will be roughly at the same speed as a standard GeoServer WMS request.  The second and subsequent map viewings will be greatly accelerated as those tiles will have already been generated.  The main advantage to this method is that it requires no preprocessing, and that only the data that has been requested will be cached, thus potentially saving disk space as well.  The disadvantage to this method is that map viewing will be only intermittently accelerated, reducing the quality of user experience.

The other way for tiles to be generated is by **seeding**.  Seeding is the process where map tiles are generated and cached internally from GeoWebCache.  When processed in advance, the user experience is greatly enhanced, as the user never has to wait for tiles to be generated.  The disadvantage to this process is that seeding can be a very time- and disk-consuming process.

In practice, a combination of both methods are usually used, with certain zoom levels (or certain areas of zoom levels) seeded, and the less-likely-viewed tiles are left uncached.

Seeding options
---------------

The :ref:`gwc_demo` contains a link next to each layer entitled :guilabel:`Seed this layer`. This link will trigger authentication with the GeoServer configuration.  Use the same username and password that you would use to log in to the :ref:`web_admin`.  (See :ref:`webadmin_basics` for more information.)  After a successful login, a new page shows up with seeding options.

The seeding options page contains various parameters for configuring the way that the layer is seeded.

.. list-table::
   :widths: 20 80
   :header-rows: 1

   * - Option
     - Description
   * - ``Number of threads to use``
     - Possible values are between **1** and **16**.
   * - ``Type of operation``
     - Sets the operation.  There are three possible values:  **Seed** (creates tiles, but does not overwrite existing ones), **Reseed** (like Seed, but overwrites existing tiles) and **Truncate** (deletes all tiles within the given parameters)
   * - ``SRS``
     - Specifies the projection to use when creating tiles (default values are **EPSG:4326** and **EPSG:900913**)
   * - ``Format``
     - Sets the image format of the tiles.  Can be **application/vnd.google-earth.kml+xml** (Google Earth KML), **image/gif** (GIF), **image/jpeg** (JPEG), **image/png** (24 bit PNG), and **image/png8** (8 bit PNG)
   * - ``Zoom start``
     - Sets the minimum zoom level.  Lower values indicate map views that are more zoomed out.  When seeding, GeoWebCache will only create tiles for those zoom levels inclusive of this value and ``Zoom stop``. 
   * - ``Zoom stop``
     - Sets the maximum zoom level.  Higher values indicate map views that are more zoomed in.  When seeding, GeoWebCache will only create tiles for those zoom levels inclusive of this value and ``Zoom start``.
   * - ``Bounding box``
     - *(optional)*  Allows seeding to occur over a specified extent, instead of the full extent of the layer.  This is useful if your layer contains data over a large area, but the application will only request tiles from a subset of that area.  The four boxes correspond to **Xmin**, **Ymin**, **Xmax**, and **Ymax**.
   
When finished, click :guilabel:`Submit`.

.. warning:: Currently there is no progress bar to inform you of the time required to perform the operation, nor is there any intelligent handling of disk space.  In short, the process may take a *very* long time, and the cache may fill up your disk.  You may wish to set a :ref:`gwc_diskquota` before running a seed job.

Manually deleting cached content
--------------------------------

If you have direct access to the file system on the server, you can also delete the appropriate layers in the cache directory.  The structure of the cache directory is ``[root]`` / ``layer`` / ``projection_zoomlevel``.

